<?php
include_once("html_head.html");
?>

<body>
<?php
include_once("html_navbar.php");
?>
  <h1>Der neue Rasierer</h1>
  <!-- Add your site or application content here -->
  <article>
    <header>
      <img width="500" src="https://upload.wikimedia.org/wikipedia/commons/f/f3/Navaja_n%C3%A1car_%28imit%29.jpg">
      <h2>Der kann nicht viel und kostet dafür mehr</h2>
      <p>Weil er sich so schlecht verkauft:</p>
      <span class="discount-price">€73,99</span> statt <span class="regular-price">€75,99</span>
    </header>
  </article>

  <script src="js/vendor/modernizr-3.11.2.min.js"></script>
  <script src="js/plugins.js"></script>
  <script src="js/main.js"></script>

  <!-- Google Analytics: change UA-XXXXX-Y to be your site's ID. -->
  <script>
    window.ga = function () { ga.q.push(arguments) }; ga.q = []; ga.l = +new Date;
    ga('create', 'UA-XXXXX-Y', 'auto'); ga('set', 'anonymizeIp', true); ga('set', 'transport', 'beacon'); ga('send', 'pageview')
  </script>
  <script src="https://www.google-analytics.com/analytics.js" async></script>
</body>

</html>
