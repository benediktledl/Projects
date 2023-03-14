<?php
include_once("html_head.html");
?>

<body>
<?php
include_once("html_navbar.php");
?>
<form id="form" action="registration.php" method="POST" enctype="multipart/form-data">
  <h2>Benutzer registrieren</h2>
  <div class="container form userform">
    <div class="row">
      <label class="col-3" for="Username">Benutzername</label>
      <input required class="col-6" type="text" id="Username" name="username">
    </div>
    <div class="row">
      <label class="col-3" for="Email">Email</label>
      <input required class="col-6" type="email" id="Email" name="email">
    </div>
    <div class="row">
      <label class="col-3" for="Passwort">Passwort</label>
      <input required class="col-6" type="password" id="Passwort" name="password">
    </div>
    <div class="row">
      <label class="col-3" for="Passwort">Passwort</label>
      <input required class="col-6" type="password" id="Passwort2" name="Passwortcheck">
    </div>
  </div>
  <div class="container form detailsform">
    <div class="row">
      <label class="col-3" for="Vorname">Vorname</label>
      <input required class="col-6" type="text" id="Vorname" name="fname">
    </div>
    <div class="row">
      <label class="col-3" for="Nachname">Nachname</label>
      <input required class="col-6" type="text" id="Nachname" name="lname">
    </div>
    <div class="row">
      <label class="col-3" for="Telefon">Telefon</label>
      <input required class="col-6" type="tel" id="Telefon" pattern="^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$" name="telephone">
    </div>
    <div class="row">
      <label class="col-3" for="Strasse">Strasse</label>
      <input required class="col-6" type="text" id="Strasse" name="street">
    </div>
    <div class="row">
      <label class="col-3" for="Ort">Ort</label>
      <input required class="col-6" type="text" id="Ort" name="town">
    </div>
    <div class="row">
      <label class="col-3" for="Plz">Plz</label>
      <input required class="col-6" type="number" id="Plz" name="zip">
    </div>
    <div class="row">
      <label class="col-3" for="Bundesland">Bundesland</label>
      <select required name="state" id="Bundesland">
        <option value="" selected disabled>Auswählen</option>
        <option value="Burgenland">Burgenland</option>
        <option value="Kärnten">Kärnten</option>
        <option value="Niederösterreich">Niederösterreich</option>
        <option value="Oberösterreich">Oberösterreich</option>
        <option value="Salzburg">Salzburg</option>
        <option value="Steiermark">Steiermark</option>
        <option value="Tirol">Tirol</option>
        <option value="Vorarlberg">Vorarlberg</option>
        <option value="Wien">Wien</option>
      </select>
    </div>
    <div class="row">
      <label class="col-3" for="file">Profilbild</label>
      <input required class="col-6" name="pic" type="file">
    </div>
  </div>
  <div class="row text-center">
    <input type="submit" value="Registrieren">
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
    var data = new FormData(this);
    if($('#Passwort').val() !== $('#Passwort2').val()){
      alert("Passwörter stimmen nicht überein!");
      return;
    }

    $.ajax({
      type: "POST",
      url: actionUrl,
      data: data,
      success: function(data){
        try{
          data = JSON.parse(data);
          if((data['status'])){
            location.href="/login.php";
          }else{
            alert("Konnte Account nicht erstellen: "+data['message']);
          }
        }catch{
          alert("Something went wrong!");
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        // handle error response
        console.log(errorThrown);
      },
      cache: false,
      contentType: false,
      processData: false
    });
  });
</script>

</body>

</html>
