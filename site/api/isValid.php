<?php
	$sequent = escapeshellarg($_GET['sequent']);
 	exec("java IsValid $sequent", $output);
 	echo $output[0];
?>