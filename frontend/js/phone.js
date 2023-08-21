//====================================================
// Start
//====================================================
const EventType = {
    UA_STATE: 'UA_STATE',
    USER_MEDIA_FAILED: 'USER_MEDIA_FAILED',
    INCOMING_CALL: 'INCOMING_CALL',
    PEER_CONNECTION: 'PEER_CONNECTION',
    ADD_STREAM: 'ADD_STREAM',
    ANSWERED: 'ANSWERED',
    CONFIRMED: 'CONFIRMED',
    CONNECTION: 'CONNECTION',
    ENDED: 'ENDED',
    FAILED: 'FAILED',
    CALLING: 'CALLING',
    AUDIO_MUTED: 'AUDIO_MUTED',
    AUDIO_UN_MUTED: 'AUDIO_UN_MUTED',
    VIDEO_MUTED: 'VIDEO_MUTED',
    VIDEO_UN_MUTED: 'VIDEO_UN_MUTED',
    HOLD: 'HOLD',
    UN_HOLD: 'UN_HOLD',
    IDLE: 'IDLE',
    GET_USER_MEDIA: 'GET_USER_MEDIA'
};

let inoutNumber;
let webrtcUser;
let webrtcPass;
let webrtcDomain;
let webrtcSocket;
let component;
let ua;

window.setProperty = function (user, pass, domain, socket, componentUI) {
    inoutNumber = user;
    webrtcUser = user;
    webrtcPass = pass;
    webrtcDomain = domain;
    webrtcSocket = socket;
    component = componentUI;
}
//====================================================

let session;			// Will contain the RTC Session

let constraints = {video: true, audio: true}; // Promise to send to peer

// ========================================
// Check if browser has audio/video support
// ========================================
function hasGetUserMedia() {
    return !!(window.navigator.mediaDevices && window.navigator.mediaDevices.getUserMedia);
}

if (!hasGetUserMedia()) {
    component.$server.auEvent(EventType.GET_USER_MEDIA, "Get user media is not supported by your browser");

}

// ======================================
// Configure and activate your user agent
// ======================================
window.configureAndActivate = function () {

    let socket = new JsSIP.WebSocketInterface(webrtcSocket);
    let configuration = {
        sockets: [socket],
        uri: webrtcUser + "@" + webrtcDomain,
        password: webrtcPass,
        session_timers: false // If set to true, call will end after a minute or so
    };

    ua = new JsSIP.UA(configuration);
    ua.start();


// ==============================================
// If user agent successfully or was not successfully registrated
// ==============================================
    ua.on('connected', function (e) {
        component.$server.auEvent(EventType.UA_STATE, "connected");
    });

    ua.on('registered', function (e) {

        component.$server.auEvent(EventType.UA_STATE, "registered");
    });

    ua.on('registrationFailed', function (e) {

        component.$server.auEvent(EventType.UA_STATE, "registration failed");
    });

// ========================================
// Check for incoming and outgoing sessions
// ========================================
    ua.on('newRTCSession', function (e) {
        session = e.session;
        bindSessionEvents(session);
        if (e.originator === "remote") {
            if (session.remote_identity.uri.user === inoutNumber) {
                component.$server.auEvent(EventType.CALLING, "Calling..");
                answer();
            } else {
                component.$server.auEvent(EventType.INCOMING_CALL, "Incoming call from " + session.remote_identity.uri.user);
            }
        } else if (e.originator === "local") {
            component.$server.auEvent(EventType.CALLING, "Calling..");
        }
    });
}

// ========
// SEND DTMF
// ========
window.callSendDTMF = function (symbol) {
    let dtmfTone = document.getElementById('dtmfTone');
    try {
        dtmfTone.play();
    } catch (e) {
    }
    let options = {
        'transportType': 'RFC2833'
    };
    session.sendDTMF(symbol, options);

}

// ========
// END CALL
// ========
window.endCall = function () {
    session.terminate();
}

// ========
// LOCAL UNHOLD\HOLD
// ========
window.localHold = function () {
    if (session.isOnHold().local === true) {
        session.unhold({
            'local': true,    // User has put the other peer on hold
            'remote': false   // Peer hasn't put user on hold
        });
    } else {
        session.hold({
            'local': true,    // User has put the other peer on hold
            'remote': false   // Peer hasn't put user on hold
        });
    }

}

// ========
// UNMUTE\MUTE AUDIO
// ========
window.muteAudio = function () {
    if (session.isMuted().audio === true) {
        session.unmute({
            audio: true,
            video: false
        });

    } else if (session.isMuted().audio === false) {
        session.mute({
            audio: true,
            video: false
        });
    }
}

// ========
// UNMUTE\MUTE VIDEO
// ========
window.muteVideo = function () {
    if (session.isMuted().video === true) {
        session.unmute({
            audio: false,
            video: true
        });

    } else if (session.isMuted().video === false) {
        session.mute({
            audio: false,
            video: true
        });
    }
}

// =====================
// Make an outgoing call
// =====================
window.makeCall = function (dialNum) {
    let options = {
        bindSessionEvents,
        constraints,
        'pcConfig': {
            'rtcpMuxPolicy': 'require',
            'iceServers': []
        }
    };

    session = ua.call('sip:' + dialNum + '@' + webrtcDomain, options);
}

// ===========
// ANSWER CALL
// ===========
window.answer = function () {
    let localStream = new MediaStream();
    let options = {
        localStream,
        constraints,
        'pcConfig': {
            'rtcpMuxPolicy': 'require',
            'iceServers': []
        }
    }
    session.answer(options);
}

// ===================================
// Bind events for the current session
// ===================================
window.bindSessionEvents = function (session) {

    session.on('getusermediafailed', function () {
        component.$server.auEvent(EventType.USER_MEDIA_FAILED, "No premission to access camera or microphone");
    });

    session.on('muted', function (e) {
        if (e.audio === true) {

            component.$server.auEvent(EventType.AUDIO_MUTED, "Microphone muted");
        } else if (e.video === true) {

            component.$server.auEvent(EventType.VIDEO_MUTED, "Camera unmuted");
        }
    });

    session.on('unmuted', function (e) {
        if (e.audio === true) {

            component.$server.auEvent(EventType.AUDIO_UN_MUTED, "Microphone unmuted");
        } else if (e.video === true) {

            component.$server.auEvent(EventType.VIDEO_UN_MUTED, "Camera unmuted");
        }
    });

    session.on('hold', function (e) {

        component.$server.auEvent(EventType.HOLD, "Local channel hold");
    });

    session.on('unhold', function (e) {

        component.$server.auEvent(EventType.UN_HOLD, "Local channel unhold");
    });


    session.on('confirmed', function (e) {


        if (session.remote_identity.uri.user !== inoutNumber) {
            component.$server.auEvent(EventType.ANSWERED, "Call answered");

        }
    });

    session.on('accepted', function (e) {

        stopRingBackTone();
        stopRingTone();

    });

    session.on('cancel', function (e) {

        stopRingBackTone();
        stopRingTone();

    });

    session.on('bye', function (e) {
        stopRingBackTone();
        stopRingTone();

    });

    session.on('failed', function (e) {
        stopRingBackTone();
        stopRingTone();

        component.$server.auEvent(EventType.FAILED, "Call failed");
        setTimeout(function () {
            component.$server.auEvent(EventType.IDLE, "idle");
        }, 5000);
    });

    session.on('rejected', function (e) {

        stopRingBackTone();
        stopRingTone();

    });

    session.on('ended', function (e) {

        component.$server.auEvent(EventType.ENDED, "Call is ended");

        setTimeout(function () {
            component.$server.auEvent(EventType.IDLE, "idle");
        }, 5000);
    });

    if (session.direction === "incoming") {
        startRingTone();
        session.on('peerconnection', function (e) {

            component.$server.auEvent(EventType.PEER_CONNECTION, "Call peer connection");

            e.peerconnection.ontrack = () => {

                let remoteStream = new MediaStream();
                e.peerconnection.getReceivers().forEach((receiver) => {
                    remoteStream.addTrack(receiver.track);
                });

                let remoteAudio = document.getElementById("localAudio");
                let remoteVideo = document.getElementById("localVideo");
                // Add the media stream to the audio element
                remoteAudio.srcObject = (e.stream);
                remoteVideo.srcObject = remoteStream;

            }
        });
    }
    if (session.direction === "outgoing") {
        startRingBackTone();
        session.connection.addEventListener('addstream', function (data) {
                component.$server.auEvent(EventType.ADD_STREAM, "Add stream");

                let remoteAudio = document.getElementById("localAudio");
                let remoteVideo = document.getElementById("localVideo");
                // Add the media stream to the audio element
                remoteAudio.srcObject = (data.stream);
                remoteVideo.srcObject = (data.stream);

            }
        )
        ;
    }
}

// Sound methods
window.startRingTone = function () {
    let ringTone = document.getElementById('ringTone');
    try {
        ringTone.play();
    } catch (e) {
    }
}

window.stopRingTone = function () {
    let ringTone = document.getElementById('ringTone');
    try {
        ringTone.pause();
    } catch (e) {
    }
}

window.startRingBackTone = function () {
    let ringBackTone = document.getElementById('ringBackTone');
    try {
        ringBackTone.play();
    } catch (e) {
    }
}

window.stopRingBackTone = function () {
    let ringBackTone = document.getElementById('ringBackTone');
    try {
        ringBackTone.pause();
    } catch (e) {
    }
}
