const inputs = document.querySelectorAll("input");
const signupButton = document.querySelectorAll("button")[1];

let usernameCheckFlag = false;

inputs[2].onblur = () => {
    $.ajax({
        async: false,
        type: "get",
        url: "/api/v1/auth/signup/validation/username",
        data:{
            username: inputs[2].value
        },
        dataType: "json",
        success: (response) => {
            if(response.data) {
                usernameCheckFlag = true;
                alert("사용 가능한 아이디 입니다.");
            } else {
                alert("이미 사용중인 아이디 입니다.");
            }
        },
        error: (error) => {
            if(error.status === 400) {
                alert(JSON.stringify(error.responseJSON.data));
            } else {
                console.log("요청 실패");
                console.log(error);
            }
        }
    })
}

signupButton.onclick = () => {
    let signupData = {
        name: inputs[0].value,
        email: inputs[1].value,
        username: inputs[2].value,
        password: inputs[3].value,
        usernameCheckFlag: usernameCheckFlag
    }

    $.ajax({
        async: false,
        type: "post",
        url: "/api/v1/auth/signup",
        contentType: "application/json",
        data: JSON.stringify(signupData),
        dataType: "json",
        success: (response) => {
            if(response.data) {
                alert("회원가입 완료.");
                location.href="/";
            }
        },
        error: (error) => {
            if(error.status === 400) {
                alert(JSON.stringify(error.responseJSON.data));
            } else {
                console.log("요청 실패");
                console.log(error);
            }
        }
    })
}