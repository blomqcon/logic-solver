<?php
	$locale='en_US.UTF-8';
	setlocale(LC_ALL,$locale);
	putenv('LC_ALL='.$locale);

	$sequent = escapeshellarg($_GET['sequent']);
	//$sequent = "'P,P→Q⊢Q'";
 	exec("java IsValid $sequent", $output);
 	echo $output[0];
?>