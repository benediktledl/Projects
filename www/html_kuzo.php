<form id="form" action="kundenzone.php" method="POST" enctype="multipart/form-data">
  <h2>Ihr Benutzerprofil</h2>
  <img height="60" src="data:image/png;base64,<?php echo base64_encode(hex2bin($usermeta['pic'])); ?>" alt="Profile Picture">
  <div class="container form userform">
    <div class="row">
      <label class="col-3" for="fname">Vorname</label>
      <input required class="col-6" type="text" id="Vorname" name="fname" value="<?php echo $usermeta['fname']; ?>">
    </div>
    <div class="row">
      <label class="col-3" for="lname">Name</label>
      <input required class="col-6" type="text" id="Name" name="lname" value="<?php echo $usermeta['lname']; ?>">
    </div>
    <div class="row">
      <label class="col-3" for="email">Email</label>
      <input required class="col-6" type="email" id="Email" name="email" value="<?php echo $usermeta['email']; ?>">
    </div>
    <div class="row">
      <label class="col-3" for="hone">Phone</label>
      <input required class="col-6" type="tel" id="Phone" pattern="^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$" name="phone" value="<?php echo $usermeta['tel']; ?>">
      <input type="hidden" name="action" value="updateData">
    </div>
    <input type="submit" value="Daten ändern">
  </div>
</form>

