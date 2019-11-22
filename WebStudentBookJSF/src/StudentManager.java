import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import javax.faces.context.FacesContext;

//import com.VirgileProcureur.web.jdbc.StudentDBUtil;

@ManagedBean(name="studentManager")
@SessionScoped

public class StudentManager{
	
	private List<Student> students;
	private Student current_stu;
	private Student disp_stu;
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
	
	public Student getCurrent_stu() {
		return current_stu;
	}
	
	public Student getDisp_stu() {
		return disp_stu;
	}

	public void setDisp_stu(Student disp_stu) {
		this.disp_stu = disp_stu;
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
	
	public String updating (Student stu)
	{
		current_stu=stu;
		setDisp_stu(new Student(stu.getId(),stu.getFirst_Name(),stu.getLast_Name(),stu.getEmail()));
		return "Edit-student";
	}
	
	public String updateStudent() {
		
		StudentDBUtil studentDBUtil = new StudentDBUtil();		
		if (disp_stu.getId()!=current_stu.getId()) {
			try {
				studentDBUtil.deleteStudent(current_stu.getId());
				studentDBUtil.addStudent(disp_stu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
				try {
					studentDBUtil.editStudent(current_stu.getId(), disp_stu.getFirst_Name(),disp_stu.getLast_Name(), disp_stu.getEmail());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		return "List-students";
	}
	
	public String adding(String FN, String LN, String EM)
	{
		Student stu = new Student(FN,LN,EM);
		return addStudent(stu);
	}
	
	public String addStudent(Student stu)
	{		
		students.add(stu);
		StudentDBUtil studentDBUtil = new StudentDBUtil();
		studentDBUtil.addStudent(stu);
		return"List-students";
	}
	
	public String deleteStudent(int id)
	{
		StudentDBUtil studentDBUtil = new StudentDBUtil();
		try {
			studentDBUtil.deleteStudent(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "List-students";
	}
	
	/*
	public String loadStudent(int ids)
	{
		
	}*/
}
