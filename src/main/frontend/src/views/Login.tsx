/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {useState} from "react";
import {LoginCrud} from "../utilities/LoginCrud";
import {InputText} from "primereact/inputtext";
import {Password} from "primereact/password";
import {Button} from "primereact/button";
import {FloatLabel} from 'primereact/floatlabel';
import '../styles/Login.css';

import {useNavigate} from 'react-router-dom';
import {Userx} from "../DTO/Userx";
import axios from "axios";
import {API_BASE_URL} from "../config/config";
import {useUser} from "../Contexts/AuthenticatedUserContext";

/**
 * Login component
 */

const Login = () => {

    // States
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();

    // use the user context to set the current user
    const {setCurrentUser} = useUser();

    /**
     * Handle login event and send login request to the server
     * @param e Form event
     *
     * Sets error message if login fails
     * Redirects to home page if login is successful
     *
     */
    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await LoginCrud.tryUserLogin({username, password});

            // If login was successful, get the user data
            // (see https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
            if (response.status === 200) {
                const {data} = await axios.get(`${API_BASE_URL}/api/users/me`);
                const user = new Userx(data); // Convert to Userx instance

                // Set the user in context
                setCurrentUser(user);

                localStorage.setItem('isAuthenticated', 'true');

                // Redirect to home page
                navigate('/');
            } else {
                setError('Login fehlgeschlagen. Bitte versuchen Sie es erneut.');
            }
        } catch (error: any) {
            console.error('Login fehlgeschlagen:', error);
            setError('Login fehlgeschlagen. Bitte versuchen Sie es erneut.');
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <h2>Login</h2>
                <form onSubmit={handleLogin}>
                    <FloatLabel style={{marginTop: 50}}>
                        <InputText
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            className="input-field"
                        />
                        <label htmlFor="username">Benutzername:</label>
                    </FloatLabel>

                    <FloatLabel style={{marginTop: 25}}>
                        <Password
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            feedback={false}
                            className="input-field"
                        />
                        <label htmlFor="password">Passwort:</label>
                    </FloatLabel>
                    <Button type="submit" label="Login" className="loginButton"/>
                </form>
                {error && <p style={{color: 'red', marginTop: 25}}>{error}</p>}
            </div>
        </div>
    );
};


export default Login
