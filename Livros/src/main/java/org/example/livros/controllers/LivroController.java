package org.example.livros.controllers;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.livros.models.Livro;
import org.example.livros.repositories.LivroRepository;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/livros"})
public class LivroController extends HttpServlet {
    private LivroRepository repository;
    public LivroController(){
        repository= new LivroRepository();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {



        List<Livro> sellers= repository.getLivro();


        RequestDispatcher dispatcher = request.getRequestDispatcher("/sellers.jsp");
        request.setAttribute("sellers", sellers);

        dispatcher.forward(request,resp);
    }
}
