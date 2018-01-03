$(document).ready(function() {		
		$("#bt").on("click",function() {
				var artist = $("#input-artist").val();
				var jqxhr = $.get("http://localhost:8080/getArtist/"+ artist,function(data) {
					
					if(data.length > 0)
					{
						$('#artists').find('tbody').html(
								$.map(data,function(item,index) {
									var img = (item.urlImage != null ? item.urlImage : '/img/not-available.png')

									return '<tr>' +
									       '<td><img width="150px" height="150px" src='+img+'/></td>' +
									       '<td>'+item.name+'</td>' +
									       '</tr>';
								}).join());
					}
					else
					{
						$("#exampleModalLabel").html('Not Found');
						$("#modal-body").html('Artist "'+artist+'" not found');
						$('#not-found').modal({show:true});
					}
				})
                .fail(function() {
                	     $("#exampleModalLabel").html('Error');
                	     $("#modal-body").html('Erro to execute request');
                	     $('#not-found').modal({show:true});
				});
		});
});