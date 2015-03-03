<?php
	$locale='en_US.UTF-8';
	setlocale(LC_ALL,$locale);
	putenv('LC_ALL='.$locale);

	$sequent = escapeshellarg($_GET['sequent']);
	//$sequent = "'P,P→Q⊢Q'";
 	exec("java GetTruthTable $sequent", $output);
 	echo "{\"headers\":";
 	echo $output[0];
 	echo ",\"values\":";
 	echo $output[1];
 	echo "}"
?>