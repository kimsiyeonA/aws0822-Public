<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자바스크립트로 사용자 객체 만들기</title>
</head>
<body>
	<script>
	   //Object 객체 생성법
		let obj = new Object();       //Object객체 생성자를 직접 부른다.
		obj.name= "김갑수";
		obj.age= 21;
		obj.move = move;   // 함수(메소드) 이름을 말한다
		
		function move(){			
			//alert("열심히 달린다");
			document.write("열심히 달린다");
			return;
		}
		
		document.write(obj.name+"<br>");
		document.write(obj.age+"<br>");
		obj.move();
		document.write("<br>");
		
		//리터럴(값대입방식) 객체생성법 
		let obj2 = {
				
				name : "김갑수",
				age : 21,
				move : function(){
					document.write("열심히 달린다");					
				}
		};
		
		document.write(obj2.name+"<br>");
		document.write(obj2.age+"<br>");
		obj2.move();
		document.write("<br>");
		
		//프로토타입(클래스) 함수 객체 생성하기위한 선언
		function Person(){    //프로토 타입 함수이다(생성자 함수)
			
			this.name="김갑수";     //this는  자기자신 객체를 뜻한다
			this.age = 21;
			this.move = function(){
				document.write("열심히 달린다");								
				return;
			}			
		}
		
		let p = new Person();
		document.write(p.name+"<br>");
		document.write(p.age+"<br>");
		p.move();
	
	</script>
</body>
</html>