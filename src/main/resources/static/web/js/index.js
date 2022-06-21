Vue.createApp({

  data() {
    return {
      emailClient: "",
      passwordClient: "",
      emailNewClient: "",
      passwordNewClient: "",
      firstName: "",
      lastName: "",
      emailSignIn: "",
      passwordSignIn: "",
      registerError: "",
    }
  },
  created() {


  },

  methods: {
    logIn() {
      console.log(this.emailSignIn, this.passwordSignIn)
      axios.post('/api/login', `email=${this.emailSignIn}&password=${this.passwordSignIn}`, {
        headers: { 'content-type': 'application/x-www-form-urlencoded' }
      })
        .then(response => {
          if (this.emailSignIn.includes("@bank.com")) {
            window.location.href = "/h2-console"
          } else {
            window.location.href = "/web/accounts.html"
          }
        }
        )
    },



    signUp() {
      axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.emailNewClient}&password=${this.passwordNewClient}`, {
        headers: { 'content-type': 'application/x-www-form-urlencoded' }
      })
        .then(response => {
          this.emailSignIn = this.emailNewClient
          this.passwordSignIn = this.passwordNewClient
          this.logIn()
          console.log("signed up")
        })
        .catch(error => {
          if (error.response.data == "Missing data"); {
            this.registerError = "Missing data"
          }
          if (error.response.data == "Name already in use") {
            this.registerError = "Name already in use";
          }
        })
    }
  },
}).mount("#app")


$('.form-control').on('focus blur', function (e) {
  $(this).parents('.form-group').toggleClass('focused', (e.type === 'focus' || this.value.length > 0));
}).trigger('blur');

$('#moveleft').click(function () {
  $('#textbox').animate({
    'marginLeft': "0" //moves left
  });

  $('.toplam').animate({
    'marginLeft': "100%" //moves right
  });
});

$('#moveright').click(function () {
  $('#textbox').animate({
    'marginLeft': "50%" //moves right
  });

  $('.toplam').animate({
    'marginLeft': "0" //moves right
  });
});