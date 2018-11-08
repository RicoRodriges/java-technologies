package services.impl;

import dao.TestResultDAO;
import dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestResultServiceImplTest {

    @Mock
    private TestResultDAO testResultDAO;

    @InjectMocks
    private TestResultServiceImpl testResultService;

    @Test
    public void CheckTestTest() {
//        TestDto test = mock(TestDto.class);
//        QuestionDto question = mock(QuestionDto.class);
//        AnswerDto answerCorrect = mock(AnswerDto.class);
//        AnswerDto answerIncorrect = mock(AnswerDto.class);
//        UserDto user = mock(UserDto.class);
//
//        when(test.getQuest()).thenReturn(Collections.singletonList(question));
//        when(test.getId()).thenReturn(1L);
//        when(question.getAnswers()).thenReturn(Arrays.asList(answerCorrect, answerIncorrect));
//        when(question.getId()).thenReturn(1L);
//        when(answerCorrect.getId()).thenReturn(1L);
//        when(answerCorrect.getIsRight()).thenReturn(true);
//        when(answerIncorrect.getId()).thenReturn(2L);
//        when(answerIncorrect.getIsRight()).thenReturn(false);
//        when(user.getId()).thenReturn(1L);
//
//        HashMap<Long, List<Long>> answers = new HashMap<>();
//        answers.put(1L, Collections.singletonList(1L));
//        TestResultDto testResult = testResultService.CheckTest(test, answers, user);
//        assertEquals(Integer.valueOf(1), testResult.getCountAnswers());
//        assertEquals(Integer.valueOf(1), testResult.getCorrectAnswers());
//
//        answers.replace(1L, Collections.singletonList(2L));
//        testResult = testResultService.CheckTest(test, answers, user);
//        assertEquals(Integer.valueOf(1), testResult.getCountAnswers());
//        assertEquals(Integer.valueOf(0), testResult.getCorrectAnswers());
    }

}
