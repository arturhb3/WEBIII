package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.models.Department;
import br.edu.ifpr.foz.ifprstore.models.Seller;
import org.junit.jupiter.api.Test;
import br.edu.ifpr.foz.ifprstore.repositories.DepartmentRepository;

import java.util.List;

public class DepartmentRepositoryTest {


    @Test
    public void deveExibirUmaListaDeDepartamentos(){

        DepartmentRepository repository = new DepartmentRepository();



        List<Department> departments = repository.getDepartaments();

        for (Department d: departments) {
            System.out.println(d);
        }

    }

    @Test
    public void deveInserirUmRegistroNaTabelaDepartament(){
        DepartmentRepository repository = new DepartmentRepository();

        Department departmentFake = new Department();
        departmentFake.setName("Financeiro");
        Department department = repository.insert(departmentFake);
    }

    @Test
    public void deveAtualizarONomeDeUmDepartamento(){

        DepartmentRepository repository = new DepartmentRepository();

        repository.updateName(7,"Financeiro");


    }

    @Test
    public void deveDeletarUmDepartment(){

        DepartmentRepository repository = new DepartmentRepository();
        repository.delete(8);

    }

    @Test
    public void deveRetornarUmDepartmentPeloId(){

        DepartmentRepository repository = new DepartmentRepository();
        Department department = repository.getById(1);

        System.out.println(department);
        System.out.println("Departamento: --------");
        System.out.println(department);

    }
}
