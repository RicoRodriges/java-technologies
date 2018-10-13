package services.impl;

import dao.TestResultDAO;
import entity.Answer;
import entity.Question;
import entity.TestResult;
import entity.User;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestResultServiceImplTest {

    @Mock
    private TestResultDAO testResultDAO;

    @InjectMocks
    private TestResultServiceImpl testResultService;

    @Test
    public void getAllTestResultsByUserIdTest() {
        TestResult testResult = mock(TestResult.class);
        when(testResultDAO.getAllTestResultsByUserId(anyLong())).thenReturn(Collections.singletonList(testResult));

        List<TestResult> allTestResultsByUserId = testResultService.getAllTestResultsByUserId(100);

        verify(testResultDAO).getAllTestResultsByUserId(100);

        assertEquals(1, allTestResultsByUserId.size());
        assertEquals(testResult, allTestResultsByUserId.get(0));
    }

    @Test
    public void CheckTestTest() {
        entity.Test test = mock(entity.Test.class);
        Question question = mock(Question.class);
        Answer answerCorrect = mock(Answer.class);
        Answer answerIncorrect = mock(Answer.class);
        User user = mock(User.class);

        when(test.getQuest()).thenReturn(Collections.singletonList(question));
        when(test.getId()).thenReturn(1L);
        when(question.getAnswers()).thenReturn(Arrays.asList(answerCorrect, answerIncorrect));
        when(question.getId()).thenReturn(1L);
        when(answerCorrect.getId()).thenReturn(1L);
        when(answerCorrect.getIsRight()).thenReturn(true);
        when(answerIncorrect.getId()).thenReturn(2L);
        when(answerIncorrect.getIsRight()).thenReturn(false);
        when(user.getId()).thenReturn(1L);

        HashMap<Long, List<Long>> answers = new HashMap<>();
        answers.put(1L, Collections.singletonList(1L));
        TestResult testResult = testResultService.CheckTest(test, answers, user);
        assertEquals(1, testResult.getCountAnswers());
        assertEquals(1, testResult.getCorrectAnswers());

        answers.replace(1L, Collections.singletonList(2L));
        testResult = testResultService.CheckTest(test, answers, user);
        assertEquals(1, testResult.getCountAnswers());
        assertEquals(0, testResult.getCorrectAnswers());
    }

}
