  $(function() {
    // run the currently selected effect
    function runEffect() {
      // get effect type from
      var selectedEffect = "scale";
      // most effect types need no options passed by default
      var options = {percent: 0};
      // run the effect
      $( "#pop-up-window" ).hide( selectedEffect, options, 1000);
    };
    // set effect from select menu value
    $( "#hidePopUpButton" ).click(function() {
      $("#carInfoTable tbody").empty();
      runEffect();
    });
  });