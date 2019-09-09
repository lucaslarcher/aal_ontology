<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ontology</title>
</head>
<body>
	<form name="f1" method="get" action="#">
		Classes:<select name="class">
				<% 
				String line;
				Process p;
				//run
				p = Runtime.getRuntime().exec("java -jar getClass.jar aal.owl");
				//p = Runtime.getRuntime().exec("ls");
				//get return
				BufferedReader stdInput = new BufferedReader(new 
				InputStreamReader(p.getInputStream()));
			    //print return
			    ArrayList<String> output = new ArrayList<String>();
			    while ((line = stdInput.readLine()) != null){
				    output.add(line);
				}
			    ArrayList<String> definitiveInfos = new ArrayList<String>();
				boolean classPhrase = false;
				for(int i=0;i<output.size();i++) {
					if(!classPhrase) {
						if(output.get(i).contains("<")) {
							classPhrase = true;
							definitiveInfos.add(output.get(i).split("#")[1].split(">")[0]);
						}
					}
					else
						definitiveInfos.add(output.get(i).split("#")[1].split(">")[0]);
				}
			
				for(int i=0;i<definitiveInfos.size();i++)
					out.print("<option value='"+definitiveInfos.get(i)+"'>"+definitiveInfos.get(i)+"</option>");
				%>
		</select>
		<input type="submit" name="submit" value="Select Class"/>
	</form>
	<%
     	String s=request.getParameter("class");
		if (s!=null)
		{
			out.println("<br />");
			out.println("<form name='f2' method='get' action='#'>");
			
			out.println("<select hidden name='class'>");
			out.print("<option value='"+s+"' selected>"+s+"</option>");
			
			out.println("</select>");
			
			out.println("Individuals of "+s+":<select name='individual'>");
			p = Runtime.getRuntime().exec("java -jar getIndividualForClass.jar aal.owl "+s);
			//p = Runtime.getRuntime().exec("ls");
			//get return
			stdInput = new BufferedReader(new 
			InputStreamReader(p.getInputStream()));
		    //print return
		    ArrayList<String> output2 = new ArrayList<String>();
		    while ((line = stdInput.readLine()) != null){
			    output2.add(line);
			}
		    ArrayList<String> definitiveIndividuals = new ArrayList<String>();
			boolean individualPhrase = false;
			for(int i=0;i<output2.size();i++) {
				if(!individualPhrase) {
					if(output2.get(i).contains("Individuals of:")) {
						individualPhrase = true;
					}
				}
				else
					definitiveIndividuals.add(output2.get(i).split(" : ")[1]);
			}
			
			for(int i=0;i<definitiveIndividuals.size();i++)
				out.print("<option value='"+definitiveIndividuals.get(i)+"'>"+definitiveIndividuals.get(i)+"</option>");
			

			out.println("</select>");
			out.println("<input type='submit' name='submit2' value='Select Individual'/>");
			out.println("</form>");
			
		}
		String individual=request.getParameter("individual");
		
		if (individual!=null)
		{
			out.println(individual);
			p = Runtime.getRuntime().exec("java -jar getIndividualInfo.jar aal.owl "+individual);
			//p = Runtime.getRuntime().exec("ls");
			//get return
			stdInput = new BufferedReader(new 
			InputStreamReader(p.getInputStream()));
			//print return
			ArrayList<String> output3 = new ArrayList<String>();
			while ((line = stdInput.readLine()) != null){
			    output3.add(line);
			}	//p = Runtime.getRuntime().exec("ls");
				//get return
					ArrayList<String> definitiveAllInfos = new ArrayList<String>();
					boolean individualPhrase = false;
					for(int i=0;i<output3.size();i++) {
						if(!individualPhrase) {
							if(output3.get(i).contains("Individual:")) {
								individualPhrase = true;
								definitiveAllInfos.add(output3.get(i));
							}
						}
						else
							definitiveAllInfos.add(output3.get(i));
					}
				out.print(" <br //>");
				for(int i=0;i<definitiveAllInfos.size();i++)
					
					out.print(" <br //>"+definitiveAllInfos.get(i));

		}

		
      %>
					

</body>

</html>