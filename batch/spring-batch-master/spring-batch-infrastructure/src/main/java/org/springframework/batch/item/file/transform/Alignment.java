/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.item.file.transform;

import org.springframework.util.Assert;

/**
 * @author Dave Syer
 *
 */
public enum Alignment {
	CENTER("CENTER", "center"),
	RIGHT("RIGHT", "right"),
	LEFT("LEFT", "left");

	private String code;
	private String label;
	
	private Alignment(String code, String label) {
        Assert.notNull(code, "'code' must not be null");

		this.code = code;
		this.label = label;
	}

    public Comparable<String> getCode() {
        return code;
    }

	public String getStringCode() {
		return (String) getCode();
	}

    public String getLabel() {
        if (this.label != null) {
            return label;
        }

        return getCode().toString();
    }
}
