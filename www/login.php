<?php
if(isset($_GET['logout']) || isset($_GET['session']) && $_GET['session'] == "expired"){
  session_start();
  session_destroy();
}
include_once("html_head.html");
?>

<body>
<?php
include_once("html_navbar.php");
?>
<form id="form" action="kuzologin.php" method="POST">
  <h2>Benutzer registrieren</h2>
  <?php
  if(isset($_GET['logout'])){
    echo'<p class="color-lightgreen">Erfolgreich ausgeloggt</p>';
  }
  if(isset($_GET['session']) && $_GET['session']=="expired"){
    echo '<p class="color-red">Sitzung abgelaufen</p>';
  }
  ?>
  <div class="container form userform">
    <div class="row">
      <label class="col-3" for="Username">Benutzername</label>
      <input required class="col-6" type="text" id="Username" name="Username">
    </div>
    <div class="row">
      <label class="col-3" for="Passwort">Passwort</label>
      <input required class="col-6" type="password" id="Passwort" name="Passwort">
    </div>
  </div>
  <div class="row text-center">
    <input type="hidden" name="action" value="login">
    <input type="submit" value="Anmelden">
  </div>
</form>

<script src="js/vendor/modernizr-3.11.2.min.js"></script>
<script src="js/plugins.js"></script>
<script src="js/main.js"></script>
<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>

<script>
  $('#form').submit(function(event){
    event.preventDefault();

    var form = $(this);
    var actionUrl = form.attr('action');
    var data = form.serialize();

    $.ajax({
      type: "POST",
      url: actionUrl,
      data: data,
      success: function(data){
        try{
          data = JSON.parse(data);
          if((data['status'])){
            location.href="/kundenzone.php";
          }else{
            alert("Anmeldung fehlgeschlagen: "+data['message']);
          }
        }catch{
          alert("Something went wrong!");
        }
      },
    });
  });
</script>

<!-- Google Analytics: change UA-XXXXX-Y to be your site's ID. -->
<script>
  window.ga = function () { ga.q.push(arguments) }; ga.q = []; ga.l = +new Date;
  ga('create', 'UA-XXXXX-Y', 'auto'); ga('set', 'anonymizeIp', true); ga('set', 'transport', 'beacon'); ga('send', 'pageview')
</script>
<script src="https://www.google-analytics.com/analytics.js" async></script>
</body>

</html>
