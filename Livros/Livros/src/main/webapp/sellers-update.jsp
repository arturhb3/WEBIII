
<%@ page import="java.util.List" %>
<%@ page import="org.example.livros.models.Autor" %>
<%@ page import="org.example.livros.models.Livro" %>
<%@ page import="org.example.livros.models.Status" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Autor> departments = (List<Autor>) request. getAttribute("departments");
    Livro seller = (Livro) request.getAttribute("seller");
%>





<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administração de Livros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="css/dashboard.css">

</head>
<body class="bg-light">

<!-- Menu superior -->
<nav class="navbar navbar-expand-lg shadow-sm navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img src="images/logo.png" alt="">IFPR Store</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Livros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Relatórios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Configurações</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">

        <!-- Barra lateral -->
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
            <div class="position-sticky pt-3">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">
                            <span data-feather="home"></span>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="file"></span>
                            Pedidos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="shopping-cart"></span>
                            Produtos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="users"></span>
                            Livros
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="bar-chart-2"></span>
                            Relatórios
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="layers"></span>
                            Integrações
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Conteúdo principal -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Livros Cadastrados</h1>

                <a class="btn btn-info" href="<%= request.getContextPath() %>/livros/create">cadastrar</a>

            </div>

            <form action="<%=request.getContextPath()%>/livros/update" method="post">

                <div class="mb-3">
                    <label for="name" class="form-label">Id: </label>
                    <input type="text" class="form-control" id="id" name="field_id" value="<%=seller.getId()%>" readonly>
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">Nome: </label>
                    <input type="text" class="form-control" id="name" name="field_name" value="<%=seller.getName()%>">
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Status: </label>
                    <select type="text" class="form-control" id="email" name="field_email" value="<%=seller.getAutor()%>" placeholder="nome@examplo.com">
                        <option selected>Selecione um status...</option>
                        <% for (Status status: Status.values()){%>
                        <% String selected=(status.name().equals(seller.getAutor()))?"selected":""; %>
                        <option <%= selected %> value="<%=status.name()%>">
                            <%= status.name()%>
                        </option>;
                        <%}%>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="birthDate" class="form-label">Data de Lançamento: </label>
                    <input type="date" class="form-control" value="<%=seller.getDataLancamento()%>" id="birthDate" name="field_birthDate">
                </div>


                <div class="mb-3">
                    <label for="department" class="form-label">Autor: </label>
                    <select class="form-select" name="field_department" id="department">
                        <option selected>Selecione um autor...</option>
                            <% for (Autor department: departments){%>
                                <% String selected=(seller.getStatus().getId() == department.getId())?"selected":""; %>
                                <option <%= selected %> value="<%=department.getId()%>">
                                    <%= department.getName()%>
                                </option>;
                            <%}%>
                    </select>
                </div>

                <div class="col-12">
                    <button class="btn btn-primary btn-sm px-5" type="submit">atualizar</button>
                </div>


            </form>

        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>