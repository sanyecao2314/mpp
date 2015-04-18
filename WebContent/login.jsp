<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/default.css" type="text/css" />
	<title>Master Profile System</title>
	</head>
	<body>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div class="logotext"> Master Profile Platform</div>
				</td>
			</tr>
			<tr>
				<td height="30">
				</td>
			</tr>
			
			<tr>
				<td style="padding-left: 100px">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td valign="middle" width="80">
								<div class="tab_act">Login</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td height="5" bgcolor="#266DBB">
				</td>
			</tr>
			
			
			<tr>
			
				<td class="loginform" align="center" style="height: 300px">
					<table>
						<tr>
							<td colspan="2" class="label">
								<h4>${param.message }</h4>
								Please type your name and password, then click the login button.
							</td>
						</tr>
					</table>
					<form action="login.action" method="post">
					<table>
						<tr>
						<td>
							<div class="label_bold">Username:</div>														
						</td>
						<td align="left">
							<input type="text" name="username" class="txt_flat" />
						</td>
						</tr>
						<tr>
						<td>
							<div class="label_bold">Password:</div>														
						</td>
						<td align="left">							
							<input type="password" name="password" class="txt_flat" />														
						</td>
						</tr>
						<tr>
							<td>
								<input type="submit" name="btn" value="Login" class="btn"/>
							</td>
						</tr>
						
					</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
