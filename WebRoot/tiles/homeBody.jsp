<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
boolean loggedIn=(request.getSession().getAttribute("permission_manager")!=null);
%>
<h2>Understanding the Paradox of Mixed Mating Systems</h2>
A NESCent Working Group</br>

<p>One of the key components of evolution by natural selection 
is reproductive success. Plants achieve reproductive success in many ways. 
One way is by self-fertilization (selfing), in which the ovule is fertilized by 
pollen produced by the same plant, often in the same flower. This is often a 'fail safe'
 method of reproduction, because the plant does not have to rely on pollinators or environmental 
 factors, or luck, to bring pollen and ovule together. This system is so effective, some plants produce 
 flowers that never even open (cleistogamy), and self-fertilize within the closed bud. However, plant 
 species or populations of the same species can be found across the entire selfing continuum-- 
 from totally selfing to mixed mating to completely outcrossing.</p>
 
 <p>
 The questions then become, why don't all plants rely completely on selfing if it is so effective? 
 And what maintains a mixture of selfing and outcrossing in some species? These are the questions members 
 of this workgroup plan to address. Many genetic, developmental, evolutionary, ecological, temporal and spatial 
 factors influence where a plant population lies on the selfing continuum. For example, plants of the same 
 species in different populations may employ different fertilization strategies depending on their geographic 
 location (central vs. the edge of their range). Thus the ecology of the species can influence mating system 
 evolution. Another complex genetic factor, inbreeding depression, is likely to play a large role. Inbreeding 
 depression is caused by the expression of deleterious recessive alleles. Selfing creates progeny that are 
 homozygous for deleterious recessive alleles and their expression causes self-progeny to be less vigorous than 
 outcross progeny. Thus inbreeding depression can reduce the reproductive success of selfing plants. Understanding 
 how disparate factors such as ecology and inbreeding depression interact to influence the evolution of selfing is 
 key to understanding how selfing evolves or mixed mating is maintained.
</p>
<%

if(!loggedIn)
{
%>

<h2>Login</h2>
<form method="post" action="login.go">
    
    <table>

	    <tr>
	    	<td class="TdField">Username</td>
	    	<td class="TdValue">

		    		<input type="text" name="userName" value=""/>

			</td>
		</tr>
		<tr>
			<td class="TdField">Password</td>
			<td class="TdValue">
    			
			    	<input type="password" name="password" value=""/>
		    	
			</td>
		</tr>
				
		<tr>
			<td></td>
			<td>
				<input type="submit" value="login" />
			</td>
		</tr>
		</table>
		
<!-- 
		<h4>Forgot your username and password? <a href="/nead/jsp/forgotpassword.jsp">Please retrieve it.</a></h4> 
		
		<br/>
		
		

			<h4>To report bugs, Please contact <a href="m&#97;i&#108;t&#x6f;:&#110;&#101;a&#x64;&#x40;ne&#x73;ce&#110;&#116;&#46;&#111;r&#103;">&#x6e;ea&#100;&#x40;n&#101;&#115;c&#x65;nt&#x2e;&#111;r&#x67;</a></h4> 
		
		

			<h4>New user? <a href="/nead/add/cv.go?access=add">please register</a></h4> 
 -->
  	 	  

	</form>
	
	<%
	}
	%>