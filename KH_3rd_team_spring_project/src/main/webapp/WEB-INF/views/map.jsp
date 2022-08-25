<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCAmP-WOXsOmf9gPK2Un7oMejAsk_B-qog&callback=myMap"></script>

    <script>
        function myMap() {
            var mapCanvas = document.getElementById("myCanvas");
            // 여기가 위치
            var myLatlng = new google.maps.LatLng(37.499006, 127.032865);

            var mapOptions = {
                center: myLatlng, // 위치 변수를 기준으로
                zoom: 16, // 확대
                mapTypeId: google.maps.MapTypeId.ROADMAP // 로드맵 형태
            };
            var map = new google.maps.Map(mapCanvas, mapOptions);

            var contentString = '<div style="width:100px;height:50px;">KH쇼핑몰</div>';    
            
            var infowindow = new google.maps.InfoWindow({
                content: contentString,
                size: new google.maps.Size(200,100)
            });
 
            var marker = new google.maps.Marker({
                position: myLatlng,
                map: map
            });
            google.maps.event.addListener(marker, 'click', function() {
                infowindow.open(map, marker);
            });
        }
 
        google.maps.event.addDomListener(window, 'load', initialize);
</script>
<style type="text/css">
.road {
	position: absolute;
	left: 45%;
}
.myCanvas {
	position: absolute;
	left: 35%;
}
</style>
</head>
<body onload="myMap()">
	<h2 class="road">찾아오시는 길</h2><br><br><br>
	<div class="myCanvas" id="myCanvas" style="width: 500px;height: 300px;"></div>
</body>
</html>