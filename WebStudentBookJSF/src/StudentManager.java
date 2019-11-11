import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;

//import com.VirgileProcureur.web.jdbc.StudentDBUtil;

@ManagedBean(name="studentManager")
@SessionScoped
public class StudentManager {
	
	private static final long serialVersionUID = 1L;
	private List<Student> students;
	private StudentDBUtil studentDBUtil;
	@Resource(name="jdbc/studentdb")
	private DataSource dataSource;
	
	public StudentManager() {
		super();
		studentDBUtil = new StudentDBUtil(dataSource);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void loadStudents()
	{
		StudentDBUtil studentDBUtil = new StudentDBUtil();
		try {
			students = studentDBUtil.getStudents();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void attrListener(ActionEvent event){
		
		System.out.println("aaaaa");
		Student tmp = (Student)event.getComponent().getAttributes().get("student");
		System.out.println(tmp.toString());
		addStudent(tmp);
		
	}
	
	public String outcome() {
		return"List-students";
	}
	public String addStudent(Student stu)
	{
		students.add(stu);
		StudentDBUtil studentDBUtil = new StudentDBUtil();
		studentDBUtil.addStudent(stu);
		return outcome();
	}
	
	/*
	public String loadStudent(int ids)
	{
		
	}*/
}
