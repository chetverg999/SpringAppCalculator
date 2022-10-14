package ru.ivanov.spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.spring.calculator.Answer;
import ru.ivanov.spring.dao.ExpressionDAO;
import ru.ivanov.spring.models.Expression;
import ru.ivanov.spring.models.LogicExpression;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Ivanov Alexandr on 06.10.2022
 */

@Controller
@RequestMapping("/expression")
public class ExpressionController {

    private final ExpressionDAO expressionDAO; // создаем ссылку на базу данных
    private final Answer answer;

    @Autowired
    public ExpressionController(ExpressionDAO expressionDAO, Answer answer) {
        this.expressionDAO = expressionDAO;  // созается объект управления базой даннх и внедряется в следующие методы
        this.answer = answer;
    }

    @GetMapping("/index")
    public String index(Model model) { // упаковываем результат вывода базы данных в модель
        model.addAttribute("expressionDAOmodel", expressionDAO.index()); // отправляем в модели список выражений под ключем expression
        return "expression/index"; // выводим окно с базой данных
    }

    @GetMapping("/logicIndex")
    public String logicIndex(Model model) { // упаковываем результат вывода базы данных в модель
        model.addAttribute("expressionDAOmodel", expressionDAO.logicIndex()); // отправляем в модели список выражений под ключем expression
        return "expression/logicIndex"; // выводим окно с базой данных
    }

    @GetMapping("/answer")
    public String answer(Model model) {
        model.addAttribute("answerModel", answer.getAnswerList());
        model.addAttribute("logicAnswerModel", answer.getLogicAnswerList());
        model.addAttribute("lastAnswerModel", answer.lastAnswer());
        model.addAttribute("lastLogicAnswerModel", answer.lastLogicAnswer());
        return "expression/answer";
    }


    @GetMapping("/new")
    public String newExpression(
            @ModelAttribute("personalExample") Expression expression,
            @ModelAttribute("logicExampleListModel") LogicExpression logicExpression) { // создаем новый объект и передаем его в форму
        return "expression/new"; // выводим страницу с формой для заполнения
    }

    @PostMapping("/lexic")
    public String create(@ModelAttribute("personalExample") @Valid Expression expression, BindingResult bindingResultExpression,
                         @ModelAttribute("logicExampleListModel") LogicExpression logicExpression) {
        System.out.println(bindingResultExpression);
        if (bindingResultExpression.hasErrors()) {
            return "expression/new";
        }
        expressionDAO.save(expression); // вносится в историю входа
        System.out.println(expressionDAO.logicIndex().toString());
        answer.answer();
        return "redirect:/expression/answer";
    }

    @PostMapping("/logic")
    public String create(@ModelAttribute("logicExampleListModel") @Valid LogicExpression logicExpression, BindingResult bindingResultLogic,
                         @ModelAttribute("personalExample") Expression expression) {
        System.out.println(bindingResultLogic);
        if (bindingResultLogic.hasErrors()) {
            return "expression/new";
        }
        expressionDAO.logicSave(logicExpression);
        System.out.println(logicExpression.toString());
        answer.logicAnswer();
        return "redirect:/expression/answer";
    }

    @ExceptionHandler(value = {IOException.class, SQLException.class})
    public String exp(@ModelAttribute ("exception") Exception ex) {
        return "expression/new";
    }

}
