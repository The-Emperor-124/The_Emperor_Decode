# Ghid de Tuning pentru Controlul PIDF la Outtake

Acest ghid explică cum să configurezi și să tunezi controllerul PIDF pentru motoarele de outtake (flywheels), folosind clasa `OuttakePIDSubsystem` generată.

## De ce PIDF?
Motoarele au nevoie de o tensiune de bază (Feedforward) pentru a menține o viteză, plus o corecție (PID) pentru a reacționa când sarcina se schimbă (ex: când tragi un disc).

## Pașii de Tuning

### 0. Pregătire
1.  Asigură-te că ai FTC Dashboard deschis (`http://192.168.43.1:8080/dash`).
2.  Deschide clasa `OuttakePIDSubsystem` în meniul de configurare din Dashboard.
3.  Pune robotul pe un stand (roțile suspendate) sau asigură-te că poți rula motoarele în siguranță.

### 1. Setarea `TICKS_PER_REV`
În cod, găsește linia:
```java
double TICKS_PER_REV = 145.1;
```
Trebuie ajustată în funcție de motorul tău:
*   **Yellow Jacket 1150 RPM**: 145.1
*   **Yellow Jacket 1620 RPM**: 103.8
*   **Yellow Jacket 312 RPM**: 537.7
*   **Gobilda 6000 RPM (Modern)**: 28.0

### 2. Tuning Feedforward (kV) - CRITIC
Acesta face 90% din treabă. Vrem ca motorul să ajungă la viteza dorită *doar* din kV.

1.  Setează `kP`, `kI`, `kD` și `kS` la **0**.
2.  Setează un `TargetRPM` realist (ex: 1500 sau 2000).
3.  Crește încet `kV` (începe cu `0.0001` și dublează).
4.  Urmărește graficul: Linia **Viteza Actuală** trebuie să ajungă lângă **Target**.
    *   Dacă viteza e prea mică -> Crește `kV`.
    *   Dacă viteza e prea mare -> Scade `kV`.
    *   Formula teoretică de start: `kV = 1.0 / MaxRPM_Motor`.

### 3. Tuning Static Friction (kS)
Dacă la viteze foarte mici motorul nu se mișcă, crește puțin `kS` până începe să se miște. Pentru shootere de viteză mare, adesea `kS` poate rămâne 0 sau foarte mic.

### 4. Tuning PID (kP) - Corecția
Acum că viteza e *aproape* bună, adăugăm `kP` pentru a repara erorile și a menține viteza constantă când tragi.

1.  Cu `kV` setat corect, setează `kP` la o valoare mică (ex: `0.001` sau `0.01`).
2.  Pornește motoarele.
3.  Simulează o sarcină (atinge ușor roata cu ceva moale - ATENȚIE la mâini!) sau trage un disc.
4.  Dacă motorul își revine greu la viteză -> Crește `kP`.
5.  Dacă motorul oscilează (se aude "vâj-vâj-vâj") -> Scade `kP`.

**Nota:** Pentru flywheels, de obicei **kI** și **kD** rămân **0**. `kD` poate amplifica zgomotul, iar `kI` poate cauza instabilitate dacă nu e resetat corect.

## Verificare Finală
Setează viteza dorită și urmărește pe grafic. Linia de viteză reală ar trebui să urce rapid și să rămână plată pe linia de target, chiar și după ce tragi un disc.
