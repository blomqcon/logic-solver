<?php
	$locale='en_US.UTF-8';
	setlocale(LC_ALL,$locale);
	putenv('LC_ALL='.$locale);

	$sequent = escapeshellarg($_GET['sequent']);
 	exec("java GetTruthTable $sequent", $output);
 	echo "{\"headers\":";
 	echo $output[0];
 	echo ",\"mainConnectors\":";
 	echo $output[1];
 	echo ",\"values\":";
 	echo $output[2];
 	echo "}"
?>