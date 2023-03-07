<header>
  <nav>
    <div class="menu">
      <div class="menu-left">
        <div class="menu-logo">
          <a href="/" class="logo"><span id="first">Rasier</span><span id="second">top</span></a>
          <h3>Unsere Rasierer rasieren</h3>
        </div>
      </div>
      <div class="menu menu-right">
        <div class="menu-item">
          <a href="/">Startseite</a>
        </div>
        <div class="menu-item">
          <a href="produkte.php">Produkte</a>
        </div>
        <?php
        session_start();
        if(!isset($_SESSION['user'])){
          echo '<div class="menu-item">
                  <a href="registrierung.php">Registrieren</a>
                </div>';
        }
        ?>
        <div class="menu-item">
          <?php
          if(isset($_SESSION['user'])){
            echo '<a href="kundenzone.php">Kundenzone</a>';
          }else{
            echo '<a href="login.php">Login</a>';
          }
          ?>
        </div>
      </div>
    </div>
  </nav>
</header>
