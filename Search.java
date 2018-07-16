package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;



public class Search extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    
	response.setContentType("test/plain;charset=utf-8");
//	String click= request.getParameter("click");
		
		try {
			DBConnection b =new DBConnection();
			ResultSet rs = b.executeQuery("select * from qbase");
			
			ArrayList<questions> list = new ArrayList();
			while(rs.next()){
				int id = rs.getInt(1);
				String question = rs.getString(2);
				String anA = rs.getString(3);
				String anB = rs.getString(4);
				String anC = rs.getString(5);
				String anD = rs.getString(6);
				String correctAn= rs.getString(7);
				questions t = new questions();
				t.Id=id;
				t.question=question;
			//System.out.println(question);
				t.anA=anA;
				t.anB=anB;
				t.anC=anC;
				t.anD=anD;
				t.correctAn=correctAn;
				
				list.add(t);
			}
			b.close();
			
			
			Random r = new Random();
	
			Boolean flag=true;
			int[] ques= {-1,-1,-1,-1};
			int j=0;
			do{
				int randnumber = r.nextInt(list.size());
				//System.out.println(randnumber);
				for(int i=0;i<4;i++){
					if(ques[i]==randnumber){
						flag=false;
						}
					}
				if(flag==true){
				ques[j] = randnumber;
				j++;
				
				
				}
			flag=true;
			}while(j<4);
			
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			JSONObject obj=new JSONObject();
			JSONObject[] a=new JSONObject[4];
			
			for(int k=0;k<4;k++) {
				a[k]=new JSONObject();
				questions s = list.get(ques[k]);
				
				
				a[k].put("Id", s.Id);
				a[k].put("question", s.question);
				a[k].put("anA", s.anA);
				a[k].put("anB", s.anB);
				a[k].put("anC", s.anC);
				a[k].put("anD", s.anD);
				a[k].put("correctAn", s.correctAn);
				
				obj.put("question"+ Integer.toString(k+1), a[k]);
				
				}    
			
			out.print(obj.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
