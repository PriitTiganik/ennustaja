# ennustaja
Priit Tiganik

## Projekti plaan 

### Esmane plaan 23.10.2015 

*Kuidas kasutajakogemus välja näeb?*
- kasutaja käivitab programmi
- aknas palutakse tal sisestada enda pikkus, peale mida kuvatakse talle programmi poolt ennustus tema kaalu kohta
- kasutaja sisestab ka enda kaalu, peale mida ennustust muduetakse vastavalt sellele, kuidas lisarida algandmestikus ennustust täpsemaks tegi

*Mida programm teeb?*
- käivitamisel võtab ühendus postgresqli andmebaasiga ja toob sealt viimase treenitud mudeli ja seal olevad treeningandmed
- viskab akna lahti, küsib kasutajalt pikkust.
- kasutaja sisestab pikkuse, misjärel arvutatakse talle eelmise mudeli alusel välja kaal
- kasutaja on vales kaalus pettunud, kui sisestab siiski oma tegeliku kaalu
- pikkus ja kaal lisatakse Javasse salvestatud andmetele ja ka andmebaasi
- lisandunud andmetega treenitakse uus, loodetavasti täpsem mudel
- mudel salvestatakse andmebaasi

*Kui peamine teema on ära tehtud, saab antud kontseptsiooni edasi arendada, näiteks:*
- kuvada andmete sisestajal statistikat, kuidas tema andmetest mudel täpsemaks muutus
- siduda mudeli arendus R-iga
- mitte modelleerida kaalu vaid midagi keerulisemat


*Kuidas teha?*
*vorm*
peale kasutaja andmete sisestamist ja andmete saatmist andmebaasi tuuakse sama kasutaja andmed ka vormile tagasi. Nii pidavat ägedam olema.
*Andmebaas*
Esimene mõte PostgreSQL, aga Ehk sobib hoopis SQLite? See on "serverless" SQL andmebaas. Saate oma lahendusega andmebaasi faili ilusal kujul kaasa pakendada ning kõiki neid probleeme vältida. Java-maailmas kasutatakse SQLite asemel tihti H2-nimelist andmebaasi, kuna see on ise Javas kirjutatud.
