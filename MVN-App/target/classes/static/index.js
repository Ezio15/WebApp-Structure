
$(document).ready(function(){
    var total = 0;
    var completed = 0;
    var remaining = 0;
    var executedTest = '';
    var startedTest = '';
    var browser = '';
    var percent = 0;
    var ratio = '';
    function round(value, decimals) {
        return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
    }
    var start = function(){
        $.ajax({ type:"GET",
             url: "http://localhost:8080/Nicola/start",
            success: function(result){
                console.log(result);
                console.log("result");
            }
        });
    }
    start();
    var callApi = function(){
        $.ajax({type: "GET",
            url: "http://localhost:8080/Nicola/toView",
            success:function(result){
                if(result != ''){
                total = result[3];
                completed = parseInt(result[4], 10);
                remaining = total - completed;
                ratio = completed+ '/' +total;
                if(result[1] == 'executed'){
                    executedTest = result[0] + " " + result[1];
                    startedTest = "";
                }else{
                    startedTest = result[0] + " " + result[1];
                }
                browser = result[2];
                console.log(result);
                percent = round((completed/total)*100,2);
                $("#completed").html(ratio);
                $("#executed-test").html(executedTest);
                $("#started-test").html(startedTest);
                $("#totalTestcases").html(total);
                $("#remaining").html(remaining);
                $("#browser").html(browser);
                //$("#percentage").html(percent+"%");
            }
        }});
    }
    callApi();
    var callAtInterval = setInterval(callApi,5000);
    function stopCalling(){
        clearInterval(callAtInterval);
    }
    var $progress = $(".progress"),
    $bar = $(".progress__bar"),
    $text = $(".progress__text"),
    update,
    resetColors,
    speed = 1000,
    orange = 30,
    yellow = 55,
    green = 85,
    timer;

resetColors = function() {
  
  $bar
    .removeClass("progress__bar--green")
    .removeClass("progress__bar--yellow")
    .removeClass("progress__bar--orange")
    .removeClass("progress__bar--blue");
  
  $progress
    .removeClass("progress--complete");
  
};

update = function() {
  
  timer = setTimeout( function() {

   
    $text.find("em").text( percent + "%" );

    if( percent >= 100 ) {

      percent = 100;
      $progress.addClass("progress--complete");
      $bar.addClass("progress__bar--blue");
      $text.find("em").text( "Completed" );

    } else {
      
      if( percent >= green ) {
        $bar.addClass("progress__bar--green");
      }
      
      else if( percent >= yellow ) {
        $bar.addClass("progress__bar--yellow");
      }
      
      else if( percent >= orange ) {
        $bar.addClass("progress__bar--orange");
      }
      
      speed = Math.floor( Math.random() * 1000 );
      update();

    }

    $bar.css({ width: percent + "%" });

  }, speed);
  
};

setTimeout( function() {
  
  $progress.addClass("progress--active");
  update();
  
},1000);

});
