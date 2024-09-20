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

@WebServlet("/livros/update")
public class LivrosUpdateController extends HttpServlet {

    LivroRepository repository = new LivroRepository();
    StatusRepository departmentRepository = new StatusRepository();
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

       Integer id=Integer.valueOf(req.getParameter("id"));
       List<Status> departments = departmentRepository.getAll();

       req.setAttribute("departments", departments);

        Livro seller=repository.getById(id);
        req.setAttribute("seller", seller);

       RequestDispatcher dispatcher = req.getRequestDispatcher("/sellers-update.jsp");

       dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("field_id"));
        String name = req.getParameter("field_name");
        String email = req.getParameter("field_email");
        LocalDate birthDate = LocalDate.parse(req.getParameter("field_birthDate"));
        Integer departmentId= Integer.valueOf(req.getParameter("field_department"));

        Status department = new Status();
        department.setId(departmentId);

        Livro seller = new Livro();
        seller.setId(id);
        seller.setName(name);
        seller.setStatus(department);
        seller.setDataLancamento(birthDate);
        seller.setAutor(email);

        repository.update(seller);

        resp.sendRedirect(req.getContextPath()+"/livros");

    }
}
