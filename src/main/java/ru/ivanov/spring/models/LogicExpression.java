package ru.ivanov.spring.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
/**
 * @author Ivanov Alexandr on 12.10.2022
 */

public class LogicExpression {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String logicEx;

    public LogicExpression() {
    }
    public LogicExpression(String logicEx) {
        this.logicEx = logicEx;
    }

    public String getLogicEx() {
        return logicEx;
    }

    public void setLogicEx(String logicEx) {
        this.logicEx = logicEx;
    }

    @Override
    public String toString() {
        return "LogicExpression{" +
                "logicEx='" + logicEx + '\'' +
                '}';
    }
}
