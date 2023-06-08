package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.MealsConstants;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsRepository repository;

    @Override
    public void init() throws ServletException {
        repository = new MemoryMealRepository();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.mealsTo(repository.getAll(), MealsConstants.CALORIES_PER_DAY));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        assert action != null;
        if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            log.info("delete id={}", id);
            repository.delete(id);
            response.sendRedirect("meals");
        }

    }
}
