package com.madhu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.madhu.entity.Department;
import com.madhu.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
	List<Employee> findByDepartment_DepartmentName(String departmentName);
	 List<Employee> findByDepartment(Department department);
}
