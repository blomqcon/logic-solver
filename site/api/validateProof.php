<?php
	$locale='en_US.UTF-8';
	setlocale(LC_ALL,$locale);
	putenv('LC_ALL='.$locale);

	$sequent = escapeshellarg($_GET['sequent']);
	$sequent = str_replace(' ', '', $sequent);
	$proof = escapeshellarg($_GET['proof']);
 	exec("java ValidateProof $sequent $proof", $output);
 	$output[0];
 	$output[1];
 	echo "{\"result\":\"";
 	echo $output[0];
 	echo "\"";
 	echo ",\"lineNumber\":\"";
 	echo $output[1];
 	echo "\"";
 	echo "}"
?>