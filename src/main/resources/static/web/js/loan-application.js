const app = Vue.createApp({

    data() {
        return {
            loans: [],

            mortgageLoan: [],
            personalLoan: [],
            autoLoan: [],

            client: [],
            accounts: [],
            numberAccountDestiny: "VIN-XXXXXXXX",
            selectedLoan: "null",

            amount: 0,
            payment: 0,
            total: 0,
        }
    },

    created() {
        axios.get("/api/clients/current")
            .then(datos => {
                this.client = datos.data
                this.accounts = this.client.accounts
            })
            .then(error => console.log(error))

        axios.get("/api/loans")
            .then(datos => {

                this.loans = datos.data
                console.log(this.loans)

                this.mortgageLoan = this.loans.filter(loan => loan.name == "Mortgage")
                console.log(this.mortgageLoan)

                this.autoLoan = this.loans.filter(loan => loan.name == "Automovil")
                console.log(this.autoLoan)

                this.personalLoan = this.loans.filter(loan => loan.name == "Personal")
                console.log(this.personalLoan)

            })
            .catch(error => console.log)
    },

    methods: {

        createLoan() {

            Swal.fire({
                title: 'Do you want to make this transaction?',
                showDenyButton: true,
                // showCancelButton: true,
                denyButtonText: `Cancel`,
                confirmButtonText: 'Send',

            }).then((result) => {
                console.log({ loanId: this.selectedLoan.id, amount: this.amount, payment: parseInt(this.payment), accountDestiny: this.numberAccountDestiny })

                if (result.isConfirmed) {
                    axios.post("/api/loans",{loanId: this.selectedLoan.id, amount: this.amount, payment: parseInt(this.payment), accountDestiny: this.numberAccountDestiny })

                        .then(response => {
                            Swal.fire('Sent!', '', 'success')
                                .then(result => {
                                    window.location.reload()
                                })
                                .catch(error => {
                                    Swal.fire('Loan denied', error.response.data, 'error')
                                        .then(result => {
                                            window.location.reload()
                                        })
                                })
                        })

                } else if (result.isDenied) {
                    Swal.fire('Changes are not saved', '', 'error')
                }
            })
        },

        payInterest() {
            let percentage = (20 * this.amount) / 100
            this.total = this.amount + percentage
        },
        selectLoan(loan) {
            this.selectedLoan = loan;
        },
        // payments(){
        // },
        logOut() {
            axios.post("/api/logout")
                .then(response => window.location.href = "/web/login.html")
        }
    },

}).mount("#app")

document.addEventListener("DOMContentLoaded", function (event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        // Validate that all variables exist
        if (toggle && nav && bodypd && headerpd) {
            toggle.addEventListener('click', () => {
                // show navbar
                nav.classList.toggle('show')
                // change icon
                toggle.classList.toggle('bx-x')
                // add padding to body
                bodypd.classList.toggle('body-pd')
                // add padding to header
                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink() {
        if (linkColor) {
            linkColor.forEach(l => l.classList.remove('active'))
            this.classList.add('active')
        }
    }
    linkColor.forEach(l => l.addEventListener('click', colorLink))

    // Your code to run since DOM is loaded and ready
});


