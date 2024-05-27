import React from 'react'

const Footer = () => {
    return (
        <div>
            <footer className='footer'>
                <span>User Management | All Right Reserved &copy; {new Date().getFullYear()} </span>
            </footer>
        </div>
    )
}

export default Footer;