/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.rss;

import java.util.Date;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeedImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UpdatedDateFilterTest {

    private UpdatedDateFilter fixture;
    private Date now;

    @Before
    public void setup() {
        now = new Date();
        fixture = new UpdatedDateFilter(now);
    }

    @Test
    public void testFilter() {
        SyndEntry entry = new SyndEntryImpl();
        entry.setPublishedDate(now);
        entry.setAuthor("ANDY");
        assertTrue(fixture.isValidEntry(new RssEndpoint(), new SyndFeedImpl(), entry));

        entry = new SyndEntryImpl();
        entry.setPublishedDate(now);
        entry.setAuthor("ANDY");
        assertFalse(fixture.isValidEntry(new RssEndpoint(), new SyndFeedImpl(), entry));

        entry = new SyndEntryImpl();
        entry.setPublishedDate(now);
        entry.setAuthor("FRED");
        assertTrue(fixture.isValidEntry(new RssEndpoint(), new SyndFeedImpl(), entry));
    }

}
