package by.epam.karotki.film_rating.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Button extends TagSupport {
	private boolean active;
	private String role;
	private String login;
	
	public void setLogin(String login){
		this.login = login;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException{
		try{
			JspWriter jw = pageContext.getOut();
			
			jw.write("<div class=\"btn-group\">");
			jw.write("<button type=\"button\" class=\"btn btn-info\">Действие</button>");
			jw.write("<button type=\"button\" class=\"btn btn-info dropdown-toggle\"	data-toggle=\"dropdown\">");
			jw.write("<span class=\"caret\"></span> <span class=\"sr-only\">Меню	с переключением</span>");
			jw.write("</button>");
			jw.write("<ul class=\"dropdown-menu\" role=\"menu\">");
			if(active == false){
				jw.write("<li><a href=\"Controller?command=update_account&login="+login+"&active=true\">Отменить бан</a></li>");
			}else{
				jw.write("<li><a href=\"Controller?command=update_account&login="+login+"&active=false\">Забанить</a></li>");
			}
			
			if(role.equals("User")){
				jw.write("<li><a href=\"Controller?command=update_account&login="+login+"&role=Admin\">Сделать администратором</a></li>");
			}
			if(role.equals("Admin")){
				jw.write("<li><a href=\"Controller?command=update_account&login="+login+"&role=User\">Сделать пользователем</a></li>");
			}
			
			jw.write("<li class=\"divider\"></li>");
			jw.write("<li><a href=\"#\">Удалить</a></li>");
			jw.write("</ul>");
			jw.write("</div>");
		}catch(IOException e){
			throw new JspException(e.getMessage());
		}
		
		return SKIP_BODY;
	}
}
