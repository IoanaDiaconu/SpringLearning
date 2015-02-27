package com.message.generator;

/**
 * Created by ioana.diaconu on 2/27/2015.
 */
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:message-generator/src/main/resources/context.xml")
@PrepareForTest(Time.class)
public class TestMessage {
    @Autowired
    MessageBean messageBean;

    @Test
    public void testGenerateMessage(){
        String test = "Just some testing";
        mockStatic(Time.class);
        when(Time.getDate()).thenReturn(test);
        Message message = messageBean.generateMessage();
        assertEquals(test, message.getDate());
    }


}
