package org.zalando.problem.springweb.advice;

/*
 * #%L
 * problem-handling
 * %%
 * Copyright (C) 2015 Zalando SE
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MissingServletRequestPartIT extends AdviceIT {

    @Override
    protected Object advice() {
        return new MissingServletRequestPart() {
        };
    }

    @Test
    public void multipart() throws Exception {
        mvc.perform(fileUpload(URI_HANDLER_MULTIPART)
                .file("payload1", new byte[]{0x1}))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.detail", containsString("payload2")));
    }

}