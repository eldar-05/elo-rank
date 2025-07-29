const sheetID = '1obGqTe_u0D46jmMj-G8MmpBEgWcgCbZrD5gmXc8T7Kw'; 
const sheetName = 'Лист1'; 
const url = `https://opensheet.elk.sh/${sheetID}/${sheetName}`;

function getRankInfo(elo) {
    if (elo < 1000) return { title: 'Новичок', class: 'gray', icon: 'novice.png' };
    if (elo < 1500) return { title: 'Боец', class: 'green', icon: 'fighter.png' };
    if (elo < 2000) return { title: 'Ветеран', class: 'yellow', icon: 'veteran.png' };
    if (elo < 2500) return { title: 'Эксперт', class: 'orange', icon: 'expert.png' };
    if (elo < 3000) return { title: 'Элита', class: 'red', icon: 'elite.png' };
    if (elo < 3500) return { title: 'Мастер', class: 'black', icon: 'master.png' };
    return { title: 'Легенда', class: 'purple', icon: 'legend.png' };
}

fetch(url)
    .then(res => {
        if (!res.ok) {
            throw new Error(`Ошибка HTTP: ${res.status}`);
        }
        return res.json();
    })
    .then(data => {
        const tbody = document.getElementById("data-body");
        tbody.innerHTML = ""; 

        if (data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="loading-message">Нет данных для отображения.</td></tr>';
            return;
        }

        data.sort((a, b) => parseInt(b.ELO) - parseInt(a.ELO));

        data.forEach((row, index) => { 
            const elo = parseInt(row.ELO);
            const rank = getRankInfo(elo);
            const place = index + 1; 

            let placeClass = 'place-number'; 
            if (place === 1) {
                placeClass += ' place-1'; 
            } else if (place === 2) {
                placeClass += ' place-2';
            } else if (place === 3) {
                placeClass += ' place-3'; 
            }

            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td><span class="${placeClass}">${place}.</span></td> <td>${row["ФИО"]}</td>
                <td class="rank-cell">
                    <img src="img/${rank.icon}" alt="${rank.title}" class="rank-icon">
                    <span class="rank-label ${rank.class}">${rank.title}</span>
                </td>
                <td>${elo}</td>
            `;
            tbody.appendChild(tr);
        });
    })
    .catch(error => {
        console.error("Произошла ошибка при получении данных:", error);
        const tbody = document.getElementById("data-body");
        tbody.innerHTML = `<tr><td colspan="4" class="loading-message">Ошибка загрузки данных. Пожалуйста, попробуйте позже.</td></tr>`;
    });