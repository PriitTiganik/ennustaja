*** Projekti plaan ***

* Esmane plaane 23.10.2015 *

Kuidas kasutajakogemus v�lja n�eb?
- kasutaja k�ivitab programmi
- aknas palutakse tal sisestada enda pikkus, peale mida kuvatakse talle programmi poolt ennustus tema kaalu kohta
- kasutaja sisestab ka enda kaalu, peale mida ennustust muduetakse vastavalt sellele, kuidas lisarida algandmestikus ennustust t�psemaks tegi

Mida programm teeb?
- k�ivitamisel v�tab �hendus postgresqli andmebaasiga ja toob sealt viimase treenitud mudeli ja seal olevad treeningandmed
- viskab akna lahti, k�sib kasutajalt pikkust.
- kasutaja sisestab pikkuse, misj�rel arvutatakse talle eelmise mudeli alusel v�lja kaal
- kasutaja on vales kaalus pettunud, kui sisestab siiski oma tegeliku kaalu
- pikkus ja kaal lisatakse Javasse salvestatud andmetele ja ka andmebaasi
- lisandunud andmetega treenitakse uus, loodetavasti t�psem mudel
- mudel salvestatakse andmebaasi

Kui peamine teema on �ra tehtud, saab antud kontseptsiooni edasi arendada, n�iteks:
- kuvada andmete sisestajal statistikat, kuidas tema andmetest mudel t�psemaks muutus
- siduda mudeli arendus R-iga
- mitte modelleerida kaalu vaid midagi keerulisemat