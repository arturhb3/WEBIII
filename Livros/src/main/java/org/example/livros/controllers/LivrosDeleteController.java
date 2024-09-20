package org.example.livros.controllers;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.livros.repositories.LivroRepository;

import java.io.IOException;

@WebServlet("/livros/delete")
public class LivrosDeleteController extends HttpServlet {

    LivroRepository repository = new LivroRepository();
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       Integer id= Integer.valueOf(req.getParameter("id"));

       repository.delete(id);

       resp.sendRedirect(req.getContextPath()+"/livros");
    }
}
