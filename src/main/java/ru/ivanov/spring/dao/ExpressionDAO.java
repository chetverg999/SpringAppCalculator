package ru.ivanov.spring.dao;

import org.springframework.stereotype.Component;
import ru.ivanov.spring.models.Expression;
import ru.ivanov.spring.models.LogicExpression;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivanov Alexandr on 07.10.2022
 */

@Component
public class ExpressionDAO {
    private List<Expression> exampleList;
    private List<LogicExpression> logicExampleList;

    {
        exampleList = new ArrayList<>();

        exampleList.add(new Expression("1+1"));
        exampleList.add(new Expression("2+2"));
        exampleList.add(new Expression("1+2"));
    }

    {
        logicExampleList = new ArrayList<>();

        logicExampleList.add(new LogicExpression("t@f"));
        logicExampleList.add(new LogicExpression("t&f"));
        logicExampleList.add(new LogicExpression("t|f"));
    }

    public List<Expression> index() {
        return exampleList;
    }

    public List<LogicExpression> logicIndex() {
        return logicExampleList;
    }

    public void save(Expression expression) {
        exampleList.add(expression);
    }

    public void logicSave(LogicExpression logicExpression) {
        logicExampleList.add(logicExpression);
    }

    @Override
    public String toString() {
        return "ExpressionDAO{" +
                "logicExampleList=" + logicExampleList +
                '}';
    }
}