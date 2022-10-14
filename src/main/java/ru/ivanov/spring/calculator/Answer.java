package ru.ivanov.spring.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ivanov.spring.dao.ExpressionDAO;
import ru.ivanov.spring.models.Expression;
import ru.ivanov.spring.models.LogicExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivanov Alexandr on 11.10.2022
 */

@Component
public class Answer {
    private final ExpressionDAO expressionDAO;
    private final LexicalAnalysis lexicalAnalysis;
    private final LogicAnalysis logicAnalysis;
    private List<String> answerList;

    private List<String> logicAnswerList;

    @Autowired
    public Answer(ExpressionDAO expressionDAO, LexicalAnalysis lexicalAnalysis, LogicAnalysis logicAnalysis) {
        this.expressionDAO = expressionDAO;
        this.lexicalAnalysis = lexicalAnalysis;
        this.logicAnalysis = logicAnalysis;
    }

    {
        logicAnswerList = new ArrayList<>();
        logicAnswerList.add("true");
        logicAnswerList.add("false");
        logicAnswerList.add("true");
    }

    {
        answerList = new ArrayList<>();
        answerList.add("0");
        answerList.add("1");
        answerList.add("2");
    }

    public void answer() {
        if (expressionDAO.index().get(expressionDAO.index().size() - 1).getEx() == null) {
            expressionDAO.index().remove(expressionDAO.index().size() - 1);
            return;
        }
        Expression example = expressionDAO.index().get(expressionDAO.index().size() - 1);
        System.out.println("0_0");
        System.out.println(example.getEx());
        lexicalAnalysis.analysis(example.getEx());
        answerList.add(lexicalAnalysis.read());
        lexicalAnalysis.clear();
    }

    public void logicAnswer() {
        if (expressionDAO.logicIndex().get(expressionDAO.logicIndex().size() - 1).getLogicEx() == null) {
            expressionDAO.logicIndex().remove(expressionDAO.logicIndex().size() - 1);
            return;
        }
        LogicExpression logicExample = expressionDAO.logicIndex().get(expressionDAO.logicIndex().size() - 1);
        System.out.println(logicExample.getLogicEx());
        System.out.println("1_1");
        logicAnalysis.analysis(logicExample.getLogicEx());
        logicAnswerList.add(logicAnalysis.readLogic());
        logicAnalysis.clearLogic();
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public List<String> getLogicAnswerList() {
        return logicAnswerList;
    }

    public String lastLogicAnswer() {
        String example = getLogicAnswerList().get(getLogicAnswerList().size() - 1);
        System.out.println(example);
        return example;
    }

    public String lastAnswer() {
        String example = getAnswerList().get(getAnswerList().size() - 1);
        System.out.println(example);
        return example;
    }
}
