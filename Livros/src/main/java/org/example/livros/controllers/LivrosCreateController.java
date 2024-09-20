package org.example.livros.controllers;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.livros.models.Livro;
import org.example.livros.models.Status;
import org.example.livros.repositories.LivroRepository;
import org.example.livros.repositories.StatusRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/livros/create")
public class LivrosCreateController extends HttpServlet {

    LivroRepository repository = new LivroRepository();
    StatusRepository departmentRepository = new StatusRepository();
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

       List<Status> departments = departmentRepository.getAll();

       req.setAttribute("departments", departments);

       RequestDispatcher dispatcher = req.getRequestDispatcher("/sellers-create.jsp");

       dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("field_name");
        String email = req.getParameter("field_email");
        LocalDate birthDate = LocalDate.parse(req.getParameter("field_birthDate"));
        Integer departmentId= Integer.valueOf(req.getParameter("field_department"));

        Status department = new Status();
        department.setId(departmentId);

        Livro seller = new Livro();
        seller.setName(name);
        seller.setStatus(department);
        seller.setDataLancamento(birthDate);
        seller.setAutor(email);

        repository.insert(seller);

        resp.sendRedirect(req.getContextPath()+"/livros");

    }
}
