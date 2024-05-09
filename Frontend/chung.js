document.addEventListener("DOMContentLoaded", function () {
    getClass();
    getSelectedClass();
    getSchedule();
});
async function getClass() {
    try {
        const response = await fetch(`http://localhost:8084/api/task/getscheduleclass`, {
            headers: {
                "Content-Type": "application/json"
            },
        });

        const data = await response.json();
        const selectedClasses = await getSelectedClassset();

        const tbody = document.querySelector('.table1 tbody');
        tbody.innerHTML = '';

        data.forEach(cls => {
            const isMatched = selectedClasses.some(s => s.scheduleclass_id === cls.id);

            const tr = document.createElement('tr');
            tr.style.backgroundColor = isMatched ? '#d7d7d7' : 'white';
            tr.innerHTML = `
                <td><input type="checkbox" ${isMatched ? 'disabled' : ''}></td>
                <td>${cls.mamh}</td>
                <td>${cls.tenmh}</td>
                <td>${cls.tengiangvien}</td>
                <td>${cls.nhom}</td>
                <td>${cls.tc}</td>
                <td>${cls.slot}</td>
                <td>${cls.conlai}</td>
                <td>${cls.lichhoc}</td>
            `;
            tbody.appendChild(tr);
            tr.querySelector('input[type="checkbox"]').addEventListener('click', handleCheckboxClick(cls.id, cls.mamh));
        });
    } catch (error) {
        console.error('Error:', error);
    }
}
function handleCheckboxClick(id, mamh) {
    return function (event) {
        const isChecked = event.target.checked;
        if (isChecked) {
            addclass(id)

        } else {
            console.log("Checkbox is unchecked!");
        }
    }
}
async function addclass(id) {
    await fetch(`http://localhost:8084/api/task/registerSubject/${id}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then(async response => {
            if (response.ok) {
                window.location.reload()
                return response;
            } else {
                response.text().then(text => {
                    alert(text)
                    window.location.reload();
                    throw new Error(text);
                })
            }
        })
        .catch(error => console.error('Error:', error));
}


async function getSelectedClass() {
    await fetch(`http://localhost:8084/api/task/getselectedclass`, {
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector('.table tbody');
            tbody.innerHTML = '';

            data.forEach(cls => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
            <td><button class="delete-button" onclick="deleteselectedclass(${cls.id})">X</button></td>
            <td>${cls.mamh}</td>
            <td>${cls.tenmh}</td>
            <td>${cls.nhom}</td>
            <td>${cls.tc}</td>
            <td>${cls.ngaydangky}</td>
            <td>${cls.lichhoc}</td>
        `;
                tbody.appendChild(tr);
            });
        }
        )
        .catch(error => console.error('Error:', error));
}


async function getSelectedClassset() {
    try {
        const response = await fetch(`http://localhost:8084/api/task/getselectedclass`, {
            headers: {
                "Content-Type": "application/json"
            },
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching selected classes:', error);
        return null;
    }
}

async function deleteselectedclass(id) {
    await fetch(`http://localhost:8084/api/task/deleteselectedclass/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then(response => {
            window.location.reload()
        })
        .catch(error => console.error('Error:', error));
}

async function getSchedule() {
    try {
        const selectedClasses = await getSelectedClassset();
        const newData = selectedClasses.map((item) => {
            const lichhocArray = item.lichhoc.split(/\s+/);

            const ngaybatdau = lichhocArray[0];
            const ngayketthuc = lichhocArray[2];
            const thu = lichhocArray[4];
            const timeStart = lichhocArray[6];
            const timeEnd = lichhocArray[8];
            var sohang = "";
            if (
                timeStart.localeCompare("07:00") >= 0 &&
                timeEnd.localeCompare("10:00") <= 0
            )
                sohang = "1";
            else if (
                timeStart.localeCompare("10:00") >= 0 &&
                timeEnd.localeCompare("12:00") <= 0
            )
                sohang = "2";
            else if (
                timeStart.localeCompare("13:00") >= 0 &&
                timeEnd.localeCompare("15:00") <= 0
            )
                sohang = "3";
            else if (
                timeStart.localeCompare("15:00") >= 0 &&
                timeEnd.localeCompare("18:00") <= 0
            )
                sohang = "4";
            else sohang = "5";
            return {
                id: item.id,
                mamh: item.mamh,
                tenmh: item.tenmh,
                tengiangvien: item.tengiangvien,
                timeStart: timeStart,
                timeEnd: timeEnd,
                nhom: item.nhom,
                hang: sohang,
                cot: thu,
            };
        });
        newData.forEach((item) => {
            if (item.hang == 1) {
                if (item.cot == 2) {
                    const tr = document.querySelector(".hang1cot2");
                    tr.classList.add("bg-tkb bg-border");
                    tr.innerHTML = `
            <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
              <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
              <div class="font-size13">
                  Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
              </div>
              `;
                } else if (item.cot == 3) {
                    const tr = document.querySelector(".hang1cot3");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 4) {
                    const tr = document.querySelector(".hang1cot4");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 5) {
                    const tr = document.querySelector(".hang1cot5");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 6) {
                    const tr = document.querySelector(".hang1cot6");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 7) {
                    const tr = document.querySelector(".hang1cot7");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                }
            } else if (item.hang == 2) {
                if (item.cot == 2) {
                    const tr = document.querySelector(".hang2cot2");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
            <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
              <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
              <div class="font-size13">
                  Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
              </div>
              `;
                } else if (item.cot == 3) {
                    const tr = document.querySelector(".hang2cot3");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 4) {
                    const tr = document.querySelector(".hang2cot4");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 5) {
                    const tr = document.querySelector(".hang2cot5");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 6) {
                    const tr = document.querySelector(".hang2cot6");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 7) {
                    const tr = document.querySelector(".hang2cot7");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                }
            } else if (item.hang == 3) {
                if (item.cot == 2) {
                    const tr = document.querySelector(".hang3cot2");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
            <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
              <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
              <div class="font-size13">
                  Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
              </div>
              `;
                } else if (item.cot == 3) {
                    const tr = document.querySelector(".hang3cot3");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 4) {
                    const tr = document.querySelector(".hang3cot4");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 5) {
                    const tr = document.querySelector(".hang3cot5");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 6) {
                    const tr = document.querySelector(".hang3cot6");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 7) {
                    console.log(item);
                    const tr = document.querySelector(".hang3cot7");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                }
            } else if (item.hang == 4) {
                if (item.cot == 2) {
                    const tr = document.querySelector(".hang4cot2");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
            <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
              <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
              <div class="font-size13">
                  Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
              </div>
              `;
                } else if (item.cot == 3) {
                    const tr = document.querySelector(".hang4cot3");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 4) {
                    const tr = document.querySelector(".hang4cot4");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 5) {
                    const tr = document.querySelector(".hang4cot5");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 6) {
                    const tr = document.querySelector(".hang4cot6");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 7) {
                    const tr = document.querySelector(".hang4cot7");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                }
            } else if (item.hang == 5) {
                if (item.cot == 2) {
                    const tr = document.querySelector(".hang5cot2");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
            <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
              <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
              <div class="font-size13">
                  Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
              </div>
              `;
                } else if (item.cot == 3) {
                    const tr = document.querySelector(".hang5cot3");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 4) {
                    const tr = document.querySelector(".hang5cot4");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 5) {
                    const tr = document.querySelector(".hang5cot5");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 6) {
                    const tr = document.querySelector(".hang5cot6");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                } else if (item.cot == 7) {
                    const tr = document.querySelector(".hang5cot7");
                    tr.classList.add("bg-tkb");
                    tr.innerHTML = `
              <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-black font-size16 xs-font-size13">${item.tenmh}</span>
                <div class="margin-10px-top font-size14">${item.tengiangvien}</div>
                <div class="font-size13">
                    Nhóm ${item.nhom} ${item.timeStart} - ${item.timeEnd}
                </div>
                `;
                }
            }
        });
        console.log(newData);
    } catch (error) {
        console.error(error); // Xử lý lỗi nếu có
    }
}
