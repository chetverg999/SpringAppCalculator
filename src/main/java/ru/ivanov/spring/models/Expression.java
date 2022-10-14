package ru.ivanov.spring.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Ivanov Alexandr on 07.10.2022
 */
public class Expression {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String ex;

    public Expression() {
    }
    public Expression(String ex) {
        this.ex = ex;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "ex='" + ex + '\'' +
                '}';
    }
}
