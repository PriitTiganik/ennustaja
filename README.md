# ennustaja
Autor: Priit Tiganik

### Projekti kirjeldus
Antud Java programm on loodud peamiste programmeerimispõhimõtete harjutamiseks Java ja JavaFX näitel. 

Kasutajale kuvatakse vorm, millel palutakse valida enda pikkus ja kaal. Peale andmete salvestamist pakutakse kasutajale võimalust enda pikkuse alusel ennustada kaal. Programm võtab seejärel ühendus PostgeSQL andmebaasiga, toob sealt sinna varem salvestatud pikkuse ja kaalu andmed (originaalis 200 Hong-Kongi kodaniku andmed), arvutab nende põhjal regressioonimudeli ning lõpuks ennustab selle alusel kasutajale tema kaalu. 

Ennustusele järgnevalt on kasutajal võimalus joonistada graafik modelleerimises kasutatud andmetega. Selle pealt on eraldi näha algandmed, kasutaja tegelik kaal ning see kaal, mille regressioonimudel kasutajale ennustas.

Mida rohkem kogutakse lähedase kehaehitusega kasutajaid, seda täpsemaks prognoos muutub.

Projekti on sisse jäetud "Admin" leht, mille abil on võimalik kasutaja poolt sisestatud andmed nullida.

### Kasutusjuhend
 1. vali enda pikkus sentimeetrites
 2. vali enda kaal kilogrammides
 3. vajuta "Salvesta andmed"
 4. vajuta "Ennusta kaal pikkuse alusel". Kui täppi programm paneb? 
 5. vajuta "Joonista graafik". Kas treeningandmed kajastavad sinu pikkust ja kaalu?
 6. vajuta "Uus sisestus" ning lisa veel paar andmerida. Kas ennustus muutub täpsemaks?

