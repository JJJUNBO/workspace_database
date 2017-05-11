function change() {
	var isbn = document.getElementById("input1").value;
	var title = document.getElementById("input2").value;
	var author = document.getElementById("input3").value;
	document.getElementById("change").innerHTML = isbn + "&&" + title + "&&"
			+ author;
}
function cleansearch() {

	document.getElementById("display").innerHTML = "";
	document.getElementById("input1").value = "";
	document.getElementById("input2").value = "";
	document.getElementById("input3").value = "";
	document.getElementById("change").innerHTML = "";
}

function search() {
	var xmlhttp = new XMLHttpRequest();
	var isbn = document.getElementById("input1").value;
	var title = document.getElementById("input2").value;
	var author = document.getElementById("input3").value;
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("display").innerHTML = xmlhttp.responseText;
		}
	};
	var sql = "select * from books,book_copies,library_branch where ISBN10 like'"
			+ "%25"
			+ isbn
			+ "%25"
			+ "'and Title like'"
			+ "%25"
			+ title
			+ "%25"
			+ "'and Author like'"
			+ "%25"
			+ author
			+ "%25"
			+ "'and ISBN10=book_id and book_copies.branch_id=library_branch.branch_id";

	xmlhttp.open("GET", "DBconnect?sql=" + sql, true);
	xmlhttp.send();

}
function change2(){
	var isbn = document.getElementById("input4").value;
	var branchid = document.getElementById("input5").value;
	var cardno = document.getElementById("input6").value;
	document.getElementById("checkout").innerHTML = isbn + "&&" + branchid + "&&"
	+ cardno;
}
function clearcheckout(){
	document.getElementById("checkout").innerHTML = "";
	document.getElementById("input4").value = "";
	document.getElementById("input5").value = "";
	document.getElementById("input6").value = "";	
}
function checkout(){
	var xmlhttp = new XMLHttpRequest();
	var isbn = document.getElementById("input4").value;
	var branchid = document.getElementById("input5").value;
	var cardno = document.getElementById("input6").value;
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("checkout").innerHTML = xmlhttp.responseText;
		}
	};
	xmlhttp.open("GET", "DBconnect2?isbn="+isbn+"&branchid="+branchid+"&cardno="+cardno, true);
	xmlhttp.send();
	
	
}
function change3(){
	var book_id = document.getElementById("input7").value;
	var cardno = document.getElementById("input8").value;
	var name = document.getElementById("input9").value;
	document.getElementById("checkin").innerHTML = book_id + "&&" + cardno + "&&"
	+ name;
}
function clearlocate(){
	document.getElementById("checkin").innerHTML = "";
	document.getElementById("input7").value = "";
	document.getElementById("input8").value = "";
	document.getElementById("input9").value = "";
	$("#checkin_dis").css("z-index","-1");
}
function locate(){
	var xmlhttp = new XMLHttpRequest();
	var bookid =document.getElementById("input7").value;
	var cardno =document.getElementById("input8").value;
	var name =document.getElementById("input9").value;
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			$("#checkin_dis").css("z-index","0");
			document.getElementById("checkin").innerHTML = xmlhttp.responseText;			
		}
	};
	xmlhttp.open("GET", "DBconnect3?bookid="+bookid+"&cardno="+cardno+"&name="+name, true);
	xmlhttp.send();
}
function checkin(){
	var xmlhttp = new XMLHttpRequest();
	var bookid =document.getElementById("input7").value;
	var cardno =document.getElementById("input8").value;
	var name =document.getElementById("input9").value;
	var loanid =document.getElementById("input10").value;
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("checkin2").innerHTML = xmlhttp.responseText;			
		}
	};
	xmlhttp.open("GET", "DBconnect4?bookid="+bookid+"&cardno="+cardno+"&name="+name+"&loanid="+loanid, true);
	xmlhttp.send();
	
	
}
function clearbow(){
	document.getElementById("displaybow").innerHTML = "";
	var ssn =document.getElementById("input11").value;
	var fname =document.getElementById("input12").value;
	var lname =document.getElementById("input13").value;
	var email =document.getElementById("input14").value;
	var addr =document.getElementById("input15").value;
	var city =document.getElementById("input16").value;
	var state =document.getElementById("input17").value;
	var phone =document.getElementById("input18").value;
	document.getElementById("input11").value = "";
	document.getElementById("input12").value = "";
	document.getElementById("input13").value = "";
	document.getElementById("input14").value = "";
	document.getElementById("input15").value = "";
	document.getElementById("input16").value = "";
	document.getElementById("input17").value = "";
	document.getElementById("input18").value = "";
}
function Createbow(){
	var xmlhttp = new XMLHttpRequest();
	var ssn =document.getElementById("input11").value;
	var fname =document.getElementById("input12").value;
	var lname =document.getElementById("input13").value;
	var email =document.getElementById("input14").value;
	var addr =document.getElementById("input15").value;
	var city =document.getElementById("input16").value;
	var state =document.getElementById("input17").value;
	var phone =document.getElementById("input18").value;
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("displaybow").innerHTML = xmlhttp.responseText;			
		}
	};
	xmlhttp.open("GET", "DBconnect5?ssn="+ssn+"&fname="+fname+"&lname="+lname+"&email="+email+"&addr="+addr
			+"&city="+city+"&state="+state+"&phone="+phone, true);
	xmlhttp.send();
	
}
function refresh(){
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			$("#showpaid").css("z-index","0");
			document.getElementById("Fineamt").innerHTML = xmlhttp.responseText;			
		}
	};
	xmlhttp.open("GET", "DBconnect6", true);
	xmlhttp.send();
}
function checkpaid(){
	var xmlhttp = new XMLHttpRequest();
	var cardno =document.getElementById("input19").value;
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			
			document.getElementById("Fineamt").innerHTML = xmlhttp.responseText;		
		}
	};
	xmlhttp.open("GET", "DBconnec7?cardno="+cardno, true);
	xmlhttp.send();
	
}
function clearpaid(){
	document.getElementById("Fineamt").innerHTML = "";
	document.getElementById("input19").value ="";
	
}
function confirmpaid(){
	var xmlhttp = new XMLHttpRequest();
	var cardno =document.getElementById("input19").value;
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			
			document.getElementById("Fineamt").innerHTML = xmlhttp.responseText;		
		}
	};
	xmlhttp.open("GET", "DBconnect8?cardno="+cardno, true);
	xmlhttp.send();
	
}