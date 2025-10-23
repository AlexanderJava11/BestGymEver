🏋️‍♂️🏆**BestGymEver**🏆🏋️‍♂️
-----
📊 Klassdiagram
-----
<img width="730" height="964" alt="image" src="https://github.com/user-attachments/assets/6081756e-8e68-4ce0-bfa2-cbef61911abe" />

## 📖 Om projektet

Detta är ett Java program som hanterar medlemmar i gymmet **BestGymEver**.
Programmet läser in medlemmar från en textfil, visar information om deras status och loggar PT-besök.
Det finns även stöd för **betalningar via Klarna (delbetalning)** och **slumpade motivationstips**.

-----

## ⚙️ Huvudfunktioner
✅ Läser in medlemsdata från 'members.txt'

✅ Söker efter medlem via namn eller personnummer

✅ Visar medlemsinformation i dialogruta ('JOptionPane')

✅ Avgör om medlemmen är nuvarande, tidigare eller obehörig

✅ Loggar PT-besök i 'pt-logg.txt'

✅ Hanterar betalningar inklusive Klarna delbetalning

✅ Ger slumpade motivationstips efter köp

-----

## 🕜 Programflöde

1. **🚀 Start**
   - 'Main.java' startar programmet.

2. **📁 Läs in medlemmar**
   - 'Register.java' läser in data från 'members.txt'
   - Vid fel -> visar dialogruta med felmeddelande och avslutar programmet.

3. **💬 Användarinmatning**
   - Användaren skriver **namn eller personnummer** via 'JOptionPane'.
   - Tom eller avbruten inmatning -> visar dialog "Ingen inmatning, avslutar".

4. **🔍 Sök i registret**
   - Programmet letar efter matchande medlem.
   - Ingen träff -> dialog: **Ej behörig kund** och avslutar.
   - Träff -> fortsätter till nästa steg.

5. **📑 Visa medlemsinformation**
   - Visar: namn, personnummer, medlemstyp, senaste betalning och kategori

     (**Nuvarande**, **Tidigare**, eller **Obehörig**

6. **🏋️ PT-loggning**
   - Om medlemmen är **Nuvarande** -> loggas besök i 'pt-logg.txt'

    Dialog: ✅ "Logg sparad".

   - Annars -> dialog: ⚠️ "Ingen loggning eftersom kunden inte är betalande".

8. **💳 Betalning (Betalning.java)**
   - Välj **gymkort**: Standard/ Guld / Platina.
   - Välj **betalningsmetod**

      💰 Banköverförning / 💳 PayPal / 📱 Swish / 📑 Klarna (delbetalning).

   - Vid **Klarna** Välj **3 / 6 / 12 månader**, programmet räknar ut för dig:
   
     -> månadsbelopp inkl. avgifter och ränta

     -> total summa att betala.

   - Bekräfta (JA/NEJ) -> simulering av betalning (7sekunder)

     Lyckades -> visar ✅ "Betalning genomförd" + ordernummer.

     Misslyckad -> ❌ "Betalning misslyckades".

10. **💡 Motivation**
    - 'Motivation.java' visar ett slumpat **motiverande citat** efter avslutad betalning.       

-----   


## 🧠 Klasser i projektet
-----

- Main.java                - Min huvudklass som kör programmet

- Register.java            - Läser in och hanterar medlemsregistret

- Medlem.java              - Record som lagrar information om varje medlem

- MedlemsKategori.java     - Enum för medlemsstatus

- Medlemstyp.java          - Enum för medlemsnivå (Standard, Guld eller Platina) Vi alla är platinor💎

- PtLogg.java              - Loggar PT-besök till textfil 'pt-logg.txt'

- Betalning.java           - Hanterar betalningar och Klarna delbetalningar med avgift och ränta

- MotivationsTips.java     - Ger slumpmässiga motivationstips till användaren 💪

## 📂 Filer
-----

- members.txt              - Lista över medlemmar

- pt-logg.txt              - Logg över gymbesök  

## 🧪 Tester
-----

- MedlemsTest.java         - Testar alla medlemmar läses in korrekt från textfilen

- RegisterTest.java        - Testar sökning och hantering av medlemregistret

- PtLogg.java              - Kontrollerar att PT-besök loggas rätt till fil

- MedlemstypTest.java      - Testar att medlemsnivåer (Standard, Guld, Platina) hanteras korrekt

-----
👨‍💻 Skapat av **Alexander**,  2025


